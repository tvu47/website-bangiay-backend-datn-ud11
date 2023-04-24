package com.snackman.datnud11.services.imp;

import com.snackman.datnud11.consts.BillStatus;
import com.snackman.datnud11.dto.PaymentDTO;
import com.snackman.datnud11.dto.request.DeleteProductInBillRequest;
import com.snackman.datnud11.entity.Bill;
import com.snackman.datnud11.entity.BillDetails;
import com.snackman.datnud11.entity.ProductDetail;
import com.snackman.datnud11.entity.Voucher;
import com.snackman.datnud11.repo.*;
import com.snackman.datnud11.responses.*;
import com.snackman.datnud11.services.BillDetailService;
import com.snackman.datnud11.services.BillService;
import com.snackman.datnud11.services.ProductDetailService;
import com.snackman.datnud11.services.VoucherService;
import com.snackman.datnud11.utils.customException.CustomNotFoundException;
import com.snackman.datnud11.utils.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BillServiceImp implements BillService {
    @Autowired
    BillRepository billRepository;

    @Autowired
    private BillDetailService billDetailService;

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private VoucherService voucherService;
    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private BillDetailsRepository billDetailsRepository;

    @Autowired
    @Lazy
    private BillService billService;
    
    @Override
    public Bill checkBillExist(Long id) throws CustomNotFoundException {
        Optional<Bill> optionalBill = billRepository.findById(id);
        if (optionalBill.isEmpty()){
            throw new CustomNotFoundException(ErrorMessage.ERROR_MESSAGE_NOT_FOUND.toString());
        }

        return optionalBill.get();
    }

    @Override
    public Bill save(Bill bill) {
        return this.billRepository.save(bill);
    }

    @Override
    public Bill payment(PaymentDTO paymentDTO) throws Exception {

        Bill bill = new Bill();
        bill.setCustomerId(paymentDTO.getInfo().getCustomerId());
        bill.setCustomerName(paymentDTO.getInfo().getFullName());
        bill.setCreateTime(new Date());
        bill.setStatus(0);
        bill.setAddress(paymentDTO.getInfo().getAddress());
        bill.setEmail(paymentDTO.getInfo().getEmail());
        bill.setPhone(paymentDTO.getInfo().getPhone());
        bill.setDiscount(0);
        bill.setTotalPrice(0d);
        this.billRepository.save(bill);

        Double totalPrice = 0d;

        for(PaymentDTO.ProductOrder productOrder : paymentDTO.getProductsOrder()){
            ProductDetail productDetail = this.productDetailService.findBySku(productOrder.getSku());
            productDetail.setQuatity(productDetail.getQuatity() - productOrder.getQuantity());
            this.productDetailService.save(productDetail);

            BillDetails billDetails = new BillDetails();
            billDetails.setBillId(bill.getId());
            billDetails.setProductId(productOrder.getId());
            billDetails.setProductName(productOrder.getProductName());
            billDetails.setColorName(productOrder.getColor().getColorName());
            billDetails.setSizeName(productOrder.getSize().getSizeName());
            billDetails.setQuantity(productOrder.getQuantity());
            billDetails.setCost(productOrder.getPrice());
            billDetails.setColor(productOrder.getColor().getId());
            billDetails.setSize(productOrder.getSize().getId());
            billDetails.setSaleprice(0d);
            billDetails.setValueDiscount(0d);
            billDetails.setStatus(true);
            billDetails.setCreateTime(new Date());
            this.billDetailService.save(billDetails);
            totalPrice += productOrder.getPrice();
        }

        if(paymentDTO.getVoucher() != null){
            Voucher voucher = this.voucherService.findById(paymentDTO.getVoucher().getId());
            voucher.setQuantity(voucher.getQuantity() - 1);
            this.voucherService.save(voucher);

            bill.setVoucherId(paymentDTO.getVoucher().getId());
            Integer discount = (int) (totalPrice * voucher.getValue() / 100);
            bill.setDiscount(discount);
            bill.setTotalPrice(totalPrice - discount);
        } else {
            bill.setVoucherId(-1l);
            bill.setDiscount(0);
            bill.setTotalPrice(totalPrice);
        }

        this.billRepository.save(bill);
        return bill;
    }

    @Override
    public Bill findById(Long id) throws Exception{
        Optional<Bill> optional = this.billRepository.findById(id);
        if(optional.isEmpty()){
            throw new Exception("Không tìm thấy bill " + id);
        }
        return optional.get();
    }

    @Override
    public Bill acceptBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DA_DUYET.status);
        this.save(bill);
        return bill;
    }

    @Override
    public Bill deliveredBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DA_GIAO.status);
        this.save(bill);
        return bill;
    }

    @Override
    public Bill cancelBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DA_HUY.status);
        this.save(bill);

        List<BillDetails> billDetails = this.billDetailService.findByBillId(id);
        for(BillDetails detail : billDetails){
            String sku = "P" + detail.getProductId() + "C" + detail.getColor() + "S" + detail.getSize();
            ProductDetail productDetail = this.productDetailService.findBySku(sku);
            productDetail.setQuatity(productDetail.getQuatity() + detail.getQuantity());
            this.productDetailService.save(productDetail);
        }

        return bill;
    }

    @Override
    public Bill deliverBill(Long id) throws Exception {
        Bill bill = this.findById(id);
        bill.setStatus(BillStatus.DANG_GIAO.status);
        this.save(bill);
        return bill;
    }


    @Override
    public List<BillResponse> getAllBill() throws Exception {
        List<BillResponse> bills = new ArrayList<>();
        List<Bill> list = this.billRepository.findAllOrderByStatus();

        for(Bill b : list){
            BillResponse billResponse = new BillResponse();
            billResponse.setId(b.getId());
            billResponse.setCustomerName(b.getCustomerName());
            billResponse.setStatus(b.getStatus());
            billResponse.setEmail(b.getEmail());
            billResponse.setPhone(b.getPhone());
            billResponse.setAddress(b.getAddress());
            billResponse.setCreateTime(b.getCreateTimeFormat());
            billResponse.setDetails(this.billDetailService.findByBillId(b.getId()));

            if(b.getVoucherId() != -1){
                try {
                    Voucher voucher = this.voucherService.findById(b.getVoucherId());
                    VoucherResponse voucherResponse = new VoucherResponse();
                    voucherResponse.setId(voucher.getId());
                    voucherResponse.setCode(voucher.getCode());
                    voucherResponse.setValue(voucher.getValue());
                    billResponse.setVoucher(voucherResponse);
                }catch (Exception e){
                }
                billResponse.setDiscount(b.getDiscount());
            }

            bills.add(billResponse);
        }

        return bills;
    }

    @Override
    public List<BillResponse> getAllBillByStatus(Integer status) throws Exception {
        List<BillResponse> bills = new ArrayList<>();
        List<Bill> list = this.billRepository.findAllByStatus(status);

        for(Bill b : list){
            BillResponse billResponse = new BillResponse();
            billResponse.setId(b.getId());
            billResponse.setCustomerName(b.getCustomerName());
            billResponse.setStatus(b.getStatus());
            billResponse.setEmail(b.getEmail());
            billResponse.setPhone(b.getPhone());
            billResponse.setAddress(b.getAddress());
            billResponse.setCreateTime(b.getCreateTimeFormat());
            billResponse.setDetails(this.billDetailService.findByBillId(b.getId()));

            if(b.getVoucherId() != -1){
                try {
                    Voucher voucher = this.voucherService.findById(b.getVoucherId());
                    VoucherResponse voucherResponse = new VoucherResponse();
                    voucherResponse.setId(voucher.getId());
                    voucherResponse.setCode(voucher.getCode());
                    voucherResponse.setValue(voucher.getValue());
                    billResponse.setVoucher(voucherResponse);
                }catch (Exception e){
                }
                billResponse.setDiscount(b.getDiscount());
            }

            bills.add(billResponse);
        }

        return bills;
    }

    @Override
    public BillResponse deleteProductInBill(DeleteProductInBillRequest request) throws Exception {
        BillDetails billDetails = this.billDetailService.findByProductInfo(request.getBillId(), request.getProductId(), request.getColorId(), request.getSizeId());
        Bill b = this.billService.findById(request.getBillId());
        ProductDetail productDetail = this.productDetailService.findBySku("P" + request.getProductId() + "C" + request.getColorId() + "S" + request.getSizeId());
        productDetail.setQuatity(productDetail.getQuatity() + billDetails.getQuantity());
        this.productDetailService.save(productDetail);
        this.billDetailService.delete(billDetails);
        List<BillDetails> listBillDetails = this.billDetailService.findByBillId(request.getBillId());
        Double price = 0d;
        double discount = 0;
        for(BillDetails detail : listBillDetails){
            price += (detail.getCost() * detail.getQuantity());
        }
        if(b.getVoucherId() != -1) {
            Voucher voucher = this.voucherService.findById(b.getVoucherId());
            discount =(price * voucher.getValue() / 100);
            price -= discount;
        }
        b.setDiscount((int)discount);
        b.setTotalPrice(price);
        if(price == 0){
            b.setStatus(BillStatus.DA_HUY.status);
        }
        this.billService.save(b);

        BillResponse billResponse = new BillResponse();
        billResponse.setId(b.getId());
        billResponse.setCustomerName(b.getCustomerName());
        billResponse.setStatus(b.getStatus());
        billResponse.setEmail(b.getEmail());
        billResponse.setPhone(b.getPhone());
        billResponse.setAddress(b.getAddress());
        billResponse.setCreateTime(b.getCreateTimeFormat());
        billResponse.setDetails(this.billDetailService.findByBillId(b.getId()));

        if(b.getVoucherId() != -1){
            try {
                Voucher voucher = this.voucherService.findById(b.getVoucherId());
                VoucherResponse voucherResponse = new VoucherResponse();
                voucherResponse.setId(voucher.getId());
                voucherResponse.setCode(voucher.getCode());
                voucherResponse.setValue(voucher.getValue());
                billResponse.setVoucher(voucherResponse);
            }catch (Exception e){
            }
            billResponse.setDiscount(b.getDiscount());
        }
        return billResponse;
    }

    @Override
    public Count findBillFromBeginDateToEndDate(Date start, Date end) {
        if (end.before(start)){
            throw new RuntimeException("Start date must before end date.");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        c.add(Calendar.DATE, 1);
        end = c.getTime();
        Count count = billRepository.tinhTong(start,end, 3);
        count.setBillResponseHistories(getListBillResposne(billRepository.thongKeHoaDon(start, end, 3)));
        return count;
    }

    @Override
    public Count findAllBillAmount() {
        Count count = billRepository.tinhTongAll(3);
        count.setBillResponseHistories(getListBillResposne(billRepository.findBillByStatus(3)));
        return count;
    }

    private List<BillResponseHistory> getListBillResposne(List<Bill> bills){
        List<BillResponseHistory> billResponseHistories = new ArrayList<>();
        bills.stream().forEach(bill -> {

            BillResponseHistory billResponseHistory = new BillResponseHistory(bill);
            if (bill.getVoucherId() != -1){
                billResponseHistory.setVoucher(voucherRepository.findById(bill.getVoucherId()).get());
            }
            billResponseHistories.add(billResponseHistory);
        });

        return billResponseHistories;
    }

    @Override
    public ProductDetailThongKeResponse getProductThongKe(){
        List<Long> listIdBill = getListIdBill();
        ProductDetailThongKeResponse response = billDetailsRepository.getSumPriceProduct(listIdBill);

        response.setProducts(getProductResList(listIdBill));
        return response;
    }

    @Override
    public ProductDetailThongKeResponse getProductThongKeTheoKhoangNgay(Date start, Date end) {
        List<Long> listIdBill = getListIdBillTheoNgay(start, end);
        ProductDetailThongKeResponse response = billDetailsRepository.getSumPriceProduct(listIdBill);

        response.setProducts(getProductResList(listIdBill));
        return response;
    }

    private List<ProductRes> getProductResList(List<Long> listLong){
        List<ProductRes> list = new ArrayList<>();
        billDetailsRepository.getBillDetailsByListIDBill(listLong).stream().forEach(billDetails -> {
            ProductRes productRes = new ProductRes(billDetails);
            list.add(productRes);
        });
        return list;
    }
    private List<Long> getListIdBill(){

        List<Long> longList = new ArrayList<>();
        billRepository.findBillByStatus(3).stream().forEach(bill -> {
            longList.add(bill.getId());
        });
        return longList;
    }
    private List<Long> getListIdBillTheoNgay(Date start, Date end){
        List<Long> longList = new ArrayList<>();
        if (end.before(start)){
            throw new RuntimeException("Start date must before end date.");
        }
        Calendar c = Calendar.getInstance();
        c.setTime(end);
        c.add(Calendar.DATE, 1);
        end = c.getTime();
        billRepository.thongKeHoaDon(start,end, 3).stream().forEach(bill -> {
            longList.add(bill.getId());
        });
        return longList;
    }
}
