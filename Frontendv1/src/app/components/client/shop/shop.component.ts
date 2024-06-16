import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {faHeart, faRetweet, faShoppingBag} from '@fortawesome/free-solid-svg-icons';
import {MessageService} from 'primeng/api';
import {CartService} from 'src/app/_service/cart.service';
import {CategoryService} from 'src/app/_service/category.service';
import {ProductService} from 'src/app/_service/product.service';
import {WishlistService} from 'src/app/_service/wishlist.service';
import {ProductroomService} from "../../../_service/productroom.service";


@Component({
  selector: 'app-shop',
  templateUrl: './shop.component.html',
  styleUrls: ['./shop.component.css'],
  providers: [MessageService]

})
export class ShopComponent implements OnInit {

  heart = faHeart;
  bag = faShoppingBag;
  retweet = faRetweet;

  id: number = 0;
  listProduct: any;
  listCategory: any;
  listProductNewest: any[] = [];

  rangeValues = [0, 100];

  capacityOptions = [];
  priceOptions = [
    {label: 'Dưới 10 triệu', value: 'LESS_THAN_10'},
    {label: 'Từ 10 - 15 triệu', value: 'FROM_10_TO_15'},
    {label: 'Từ 15 - 20 triệu', value: 'FROM_15_TO_20'},
    {label: 'Trên 20 triệu', value: 'OVER_THAN_20'},
  ]
  orderPriceOptions = [
    {label: 'Tăng dần', value: 'ASC'},
    {label: 'Giảm dần', value: 'DESC'},
  ]

  selectedCapacity: any = null;
  selectedPrice: any = null;
  selectedOrderPrice: any = null;


  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private router: Router,
    private productRoomService: ProductroomService,
    private route: ActivatedRoute,
    public cartService: CartService,
    private messageService: MessageService,
    public wishlistService: WishlistService) {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getProductRoom()
    this.getListProductByCategory();
    this.getListCategoryEnabled();
    this.getNewestProduct();
  }

  getProductRoom() {
    this.productRoomService.getList().subscribe({
      next: (res) => {
        this.capacityOptions = res
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  handleFilter() {
    const dataFilterOptions = {
      categoryId: this.id,
      capacity: this.selectedCapacity?.id,
      price: this.selectedPrice?.value,
      orderPrice: this.selectedOrderPrice?.value
    }
    this.productService.filter(dataFilterOptions).subscribe({
      next: (res) => {
        this.listProduct = res
      },
      error: (err) => {
        console.log(err)
      }
    })
  }

  getListProductByCategory() {
    this.productService.getListByCategory(this.id).subscribe({
      next: res => {
        this.listProduct = res;
      }, error: err => {
        console.log(err);
      }
    })
  }

  getListCategoryEnabled() {
    this.categoryService.getListCategoryEnabled().subscribe({
      next: res => {
        this.listCategory = res;
      }, error: err => {
        console.log(err);
      }
    })
  }

  getNewestProduct() {
    this.productService.getListProductNewest(4).subscribe({
      next: res => {
        this.listProductNewest = res;
      }, error: err => {
        console.log(err);
      }
    })
  }

  getListProductByPriceRange() {
    this.productService.getListByPriceRange(this.id, this.rangeValues[0], this.rangeValues[1]).subscribe({
      next: res => {
        this.listProduct = res;
        console.log(this.listProduct);
      }, error: err => {
        console.log(err);
      }
    })
  }


  addToCart(product: any) {


    // Kiểm tra số lượng sản phẩm có sẵn
    if (product.quantity <= 0) {
      this.showError("Sản phẩm đã hết hàng!");
    } else if (this.cartService.getProductQuantityInCart(product.id) > product.quantity) {
      // Kiểm tra nếu số lượng sản phẩm trong giỏ hàng cộng với số lượng muốn thêm vào lớn hơn số lượng có sẵn
      this.showError("Số lượng sản phẩm đã hết!");
    } else {
      // Nếu không có vấn đề gì, thêm sản phẩm vào giỏ hàng
      this.cartService.addToCart(product, 1);
      this.showSuccess("Thêm giỏ hàng thành công!");
    }
  }

  addToWishList(item: any) {
    if (!this.wishlistService.productInWishList(item)) {
      this.showSuccess("Thêm yêu thích thành công!")
      this.wishlistService.addToWishList(item);
    }
  }

  showSuccess(text: string) {
    this.messageService.add({severity: 'success', summary: 'Success', detail: text});
  }

  showError(text: string) {
    this.messageService.add({severity: 'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity: 'warn', summary: 'Warn', detail: text});
  }

}
