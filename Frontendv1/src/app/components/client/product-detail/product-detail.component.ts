import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {
  faBars,
  faEye,
  faHeart,
  faPhone,
  faRetweet,
  faShoppingBag,
  faStar,
  faStarHalf
} from '@fortawesome/free-solid-svg-icons';
import {CartService} from 'src/app/_service/cart.service';
import {ProductService} from 'src/app/_service/product.service';
import {WishlistService} from 'src/app/_service/wishlist.service';

import {ProductcolorService} from 'src/app/_service/productcolor.service';
import {ProductroomService} from 'src/app/_service/productroom.service';
import {ProductsizeService} from 'src/app/_service/productsize.service';
import {MessageCustomService} from "../../../_service/message-custom.service";

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css'],
  providers: [MessageCustomService]

})
export class ProductDetailComponent implements OnInit {
  selectedColor: any;
  colorsave: any;

  selectedSize: any;
  sizesave: any;

  selectedRoom: any;
  roomsave: any;

  listProductcolor: any;
  listProductroom: any[] = [];
  listProductsize: any[] = [];
  heart = faHeart;
  bag = faShoppingBag;
  phone = faPhone;
  bars = faBars;
  star = faStar;
  star_half = faStarHalf;
  retweet = faRetweet;
  eye = faEye;

  showDepartment = false;

  id: number = 0;

  product: any;
  listRelatedProduct: any[] = [];
  quantity: number = 1;

  // Khai báo biến boolean để kiểm tra xem có vượt quá giới hạn không
  exceedLimit: boolean = false;


  constructor(private productService: ProductService,
              private productroomService: ProductroomService,
              private productsizeService: ProductsizeService,
              private productcolorService: ProductcolorService,
              private router: Router,
              private route: ActivatedRoute,
              public cartService: CartService,
              public wishlistService: WishlistService,
              private messageService: MessageCustomService) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getProduct();
    this.getListProductcolor();
    this.getListProductroom();
    this.getListProductsize();
  }

  showDepartmentClick() {
    this.showDepartment = !this.showDepartment;
  }

  getListProductcolor() {
    this.productcolorService.getList().subscribe({
      next: res => {
        this.listProductcolor = res;

      }, error: err => {
        console.log(err);
      }
    })
  }

  getListProductsize() {
    this.productsizeService.getList().subscribe({
      next: res => {
        this.listProductsize = res;
      }, error: err => {
        console.log(err);
      }
    })
  }

  getListProductroom() {
    this.productroomService.getList().subscribe({
      next: res => {
        this.listProductroom = res;
      }, error: err => {
        console.log(err);
      }
    })
  }

  getProduct() {
    this.productService.getProdct(this.id).subscribe({
      next: res => {
        this.product = res;

        this.getListRelatedProduct();
      }, error: err => {
        console.log(err);
      }
    })
  }


  getListRelatedProduct() {
    this.productService.getListRelatedProduct(this.product.category.id).subscribe({
      next: res => {
        this.listRelatedProduct = res;
      }, error: err => {
        console.log(err);
      }
    })
  }


  addToCart(product: any) {

    // Kiểm tra số lượng sản phẩm có sẵn
    if (product.quantity <= 0) {
      this.messageService.showError("Sản phẩm đã hết hàng!");
    } else if (this.cartService.getProductQuantityInCart(product.id) > product.quantity) {
      // Kiểm tra nếu số lượng sản phẩm trong giỏ hàng cộng với số lượng muốn thêm vào lớn hơn số lượng có sẵn
      this.messageService.showError("Số lượng sản phẩm đã hết!");
    } else {
      // Nếu không có vấn đề gì, thêm sản phẩm vào giỏ hàng
      this.cartService.addToCart(product, 1);

      this.messageService.showSuccess("Thêm giỏ hàng thành công!");
    }
  }


  addCart(item: any) {
    console.log(item)
    // if (item?.productcolors && item?.productcolors?.length > 0 && !this.colorsave) {
    //   this.messageService.showError("Vui lòng chọn Màu sắc!");
    //   return
    // }
    // if (item?.productsizes && item?.productsizes?.length > 0 && !this.sizesave) {
    //   this.messageService.showError("Vui lòng chọn Kích thước!");
    //   return
    // }
    // if (item?.productrooms && item?.productrooms?.length > 0 && !this.roomsave) {
    //   this.messageService.showError("Vui lòng chọn Dung lượng!");
    //   return
    // }
    if ((item.category.id === 1 || item.category.id === 2 || item.category.id === 3 || item.category.id === 4 || item.category.id === 5 || item.category.id === 6 || item.category.id === 7) && !this.colorsave) {
      this.messageService.showError("Vui lòng chọn Màu sắc!");
      return
    }
    if (item.category.id === 1 && !this.sizesave) {
      this.messageService.showError("Vui lòng chọn Kích thước!");
      return
    }
    if ((item.category.id === 3 || item.category.id === 2 || item.category.id === 1) && !this.roomsave) {
      this.messageService.showError("Vui lòng chọn Dung lượng!");
      return
    }
    item.color = this.colorsave;
    item.size = this.sizesave;
    item.room = this.roomsave;
    // Kiểm tra số lượng sản phẩm
    if (item.quantity <= 0) {
      // Hiển thị thông báo sản phẩm đã hết hàng
      this.messageService.showError("Sản phẩm đã hết hàng!");
    } else {
      this.cartService.getItems();
      this.cartService.addToCart(item, this.quantity);
      this.messageService.showSuccess("Thêm giỏ hàng thành công!");
    }
  }

// Phương thức để tăng số lượng sản phẩm
  plusQuantity() {
    if (this.quantity < this.product.quantity) {
      this.quantity++;
    } else {
      this.exceedLimit = true; // Đặt biến exceedLimit thành true nếu vượt quá giới hạn
      this.messageService.showWarn("Số lượng đã vượt quá giới hạn!")
    }
  }

  validateQuantity() {
    if (this.quantity > this.product.quantity) {
      this.quantity = this.product.quantity;
    }
  }

// Phương thức để giảm số lượng sản phẩm
  subtractQuantity() {
    if (this.quantity > 1) {
      this.quantity--;
      this.exceedLimit = false; // Đặt biến exceedLimit thành false khi giảm số lượng
    }
  }

  addToWishList(item: any) {
    if (!this.wishlistService.productInWishList(item)) {
      this.wishlistService.addToWishList(item);
      this.messageService.showSuccess("Thêm yêu thích thành công!")
    }
  }


  changeColor(colorName: any): void {
    this.selectedColor = colorName;
    this.colorsave = colorName;
  }

  changeSize(sizeName: any): void {
    this.selectedSize = sizeName;
    this.sizesave = sizeName;
  }

  changeRoom(roomName: any): void {
    this.selectedRoom = roomName;
    this.roomsave = roomName;
  }
}
