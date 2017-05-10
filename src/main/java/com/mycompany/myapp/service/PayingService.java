package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Paying;
import com.mycompany.myapp.repository.PayingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
// import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.domain.CartChiTiet;
import com.mycompany.myapp.domain.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mycompany.myapp.repository.PersistentTokenRepository;
import com.mycompany.myapp.repository.CartRepository;
import com.mycompany.myapp.repository.CartChiTietRepository;
import com.mycompany.myapp.repository.PayingRepository;
import com.mycompany.myapp.service.CartChiTietService;


import javax.inject.Inject;
import java.util.List;

@Service
public class PayingService{
  private final Logger log = LoggerFactory.getLogger(PayingService.class);

  @Inject
  private UserService userService;

  @Inject
  private CartRepository cartRepository;

  @Inject
  private CartChiTietRepository cartChiTietRepository;

  @Inject
  private PayingRepository payingRepository;

  @Inject
  private CartChiTietService cartChiTietService;

  public String getIdCurrentUserLogin(){
    String id=userService.getUserWithAuthorities().getId();
    return id;
  }
  public Paying save (Paying paying){
    Cart cart= cartRepository.findByUserIdAndStatusTrue(getIdCurrentUserLogin());
    if(cart==null) return paying;// Paying paying =new Paying();

    paying.setUser_id(cart.getUserId());
    paying.setCartid(cart.getId());
    paying.setPrice(getTotalValue());
    paying.setChiTietGiaoDich(getThongTinGiaoDich());
    paying.setPriceWithVAT(getTotalValue()*11/10);

    System.out.println(paying);

    payingRepository.save(paying);
    String userId=getIdCurrentUserLogin();
    // long price=0L;
    cart.setStatus(false);
    return paying;
  }

  public Paying getPrefix (){
    Cart cart= cartRepository.findByUserIdAndStatusTrue(getIdCurrentUserLogin());
    if(cart==null) return null;// Paying paying =new Paying();

    Paying paying =new Paying();
    paying.setCartid(cart.getId());
    paying.setUser_id(cart.getUserId());
    paying.setPrice(getTotalValue());
    paying.setPriceWithVAT(getTotalValue()*11/10);
    paying.setChiTietGiaoDich(getThongTinGiaoDich());
    return paying;
  }

  public long getTotalValue(){
    String userId=getIdCurrentUserLogin();
    long price=0L;
    Cart cart= cartRepository.findByUserIdAndStatusTrue(getIdCurrentUserLogin());
    List<CartChiTiet> cartChiTiets=cartChiTietRepository.findAllByCartId(cart.getId());
    for(int i=0;i<cartChiTiets.size();i++){
      price +=cartChiTiets.get(i).getThanhtien();
    }
    return price;
  }

  public String getThongTinGiaoDich(){
    String chitietGD="";
    List<CartChiTiet> listCartCt = cartChiTietService.findAllByUser();
    if(listCartCt!=null || listCartCt.size()>0 )
    for(int i=1;i<=listCartCt.size();i++){
      // int count
      chitietGD=i+". Tên sách: "+listCartCt.get(i-1).getBookName()+" số lượng: "+listCartCt.get(i-1).getNumberOfBook() + " thành tiền "+listCartCt.get(i-1).getThanhtien()+" \n";
    }
    return chitietGD;
  }

  public List<Paying> getAllPayingsByUser(){
    return payingRepository.findAllByUser(getIdCurrentUserLogin());
  }
}
