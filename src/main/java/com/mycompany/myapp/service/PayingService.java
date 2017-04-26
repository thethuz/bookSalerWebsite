package com.mycompany.myapp.service;
import com.mycompany.myapp.domain.Paying;
import com.mycompany.myapp.repository.PayingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
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

public class PayingService{
  private CartRepository cartRepository;
  private CartChiTietRepository cartChiTietRepository;
  private PayingRepository payingRepository;
  private UserService userService;
  public String getIdCurrentUserLogin(){
    String id=userService.getUserWithAuthorities().getId();
    return id;
  }
  public Paying save (Paying paying){
    // Paying paying =new Paying();
    paying.setPrice(getTotalValue());
    paying.setPriceWithVAT(getTotalValue()*11/10);
    payingRepository.save(paying);
    String userId=getIdCurrentUserLogin();
    // long price=0L;
    Cart cart= cartRepository.findByUserIdAndStatusTrue(userId);
    cart.setStatus(false);
    return paying;
  }
  public Paying getPrefix (){
    Paying paying =new Paying();
    paying.setPrice(getTotalValue());
    paying.setPriceWithVAT(getTotalValue()*11/10);
    return paying;
  }
  public long getTotalValue(){
    String userId=getIdCurrentUserLogin();
    long price=0L;
    Cart cart= cartRepository.findByUserIdAndStatusTrue(userId);
    List<CartChiTiet> cartChiTiets=cartChiTietRepository.findAllByCartId(cart.getId());
    for(int i=0;i<cartChiTiets.size();i++){
      price +=cartChiTiets.get(i).getThanhtien();
    }
    return price;
  }


}
