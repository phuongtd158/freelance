import { Component, OnInit } from '@angular/core';
import { faEye, faHeart, faRetweet, faShoppingBag } from '@fortawesome/free-solid-svg-icons';
import { MessageService } from 'primeng/api';
import { BlogService } from 'src/app/_service/blog.service';
import { CartService } from 'src/app/_service/cart.service';
import { CategoryService } from 'src/app/_service/category.service';
import { ProductService } from 'src/app/_service/product.service';
import { WishlistService } from 'src/app/_service/wishlist.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
  providers: [MessageService]

})
export class HomeComponent implements OnInit {

  listCategoryEnabled: any;
  heart = faHeart;
  bag = faShoppingBag;
  retweet = faRetweet;
  eye = faEye;

  listProductNewest: any;
  listProductPrice: any;
  listBlogNewest: any;
  showDepartment = true;


  category_items_response = [

    {
      breakpoint: '1024px',
      numVisible: 3,
      numScroll: 3
    },
    {
      breakpoint: '768px',
      numVisible: 2,
      numScroll: 2
    },
    {
      breakpoint: '560px',
      numVisible: 1,
      numScroll: 1
    }

  ]
  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 3,
      numScroll: 3
    },
    {
      breakpoint: '768px',
      numVisible: 2,
      numScroll: 2
    },
    {
      breakpoint: '560px',
      numVisible: 1,
      numScroll: 1
    }
  ];

  category_items = [
    {
      id: 1,
      src: 'assets/image/slide4.png',
      alt: '',
      // title: 'NEW'
    },
    {
      id: 2,
      src: 'assets/image/slide1.png',
      alt: '',
      // title: 'HOT'
    },
    {
      id: 3,
      src: 'assets/image/slide2.png',
      alt: '',
      // title: 'NEW'
    },
    {
      id: 4,
      src: 'assets/image/slide3.png',
      alt: '',
      // title: 'HOT'
    },
    {
      id: 5,
      src: 'assets/image/slide4.png',
      alt: '',
      // title: 'NEW'
    }
  ];
  slides: any[] = new Array(6).fill({id: -1, src: '', title: '', subtitle: ''});

  constructor(
    private categoryService: CategoryService,
    private productSerive: ProductService,
    private blogService: BlogService,
    private cartService: CartService,
    private wishlistService: WishlistService,
    private messageService: MessageService
  ) {
  }

  ngOnInit(): void {
    this.getCategoryEnbled();
    this.getListProduct();
    this.slides[0] = {
      src: 'assets/image/slide4.png',
    };
    this.slides[1] = {
      src: 'assets/image/slide1.png',
    }
    this.slides[2] = {
      src: 'assets/image/slide2.png',
    }
    this.slides[3] = {
      src: 'assets/image/slide3.png',
    };
    this.slides[4] = {
      src: 'assets/image/slide4.png',
    }
    this.slides[5] = {
      src: 'assets/image/slide1.png',
    }
    this.getListNewest();


  }


  getListProduct() {
    this.productSerive.getListProductNewest(12).subscribe({
      next: res => {
        this.listProductNewest = res;
      }, error: err => {
        console.log(err);
      }
    })
    this.productSerive.getListProductByPrice().subscribe({
      next: res => {
        this.listProductPrice = res;
      }, error: err => {
        console.log(err);
      }
    })
  }


  addToCart(event: any, product: any) {
    event.preventDefault()
    event.stopPropagation()
    // Kiểm tra số lượng sản phẩm có sẵn
    if (product.quantity <= 0) {
      this.showError("Sản phẩm đã hết hàng!");
    } else {
      // Nếu không có vấn đề gì, thêm sản phẩm vào giỏ hàng
      this.cartService.addToCart(product, 1);
      this.showSuccess("Thêm giỏ hàng thành công!");
    }
    console.log(product)
  }


  getCategoryEnbled() {
    this.categoryService.getListCategoryEnabled().subscribe({
      next: res => {
        this.listCategoryEnabled = res;
      }, error: err => {
        console.log(err);
      }
    })
  }


  addToWishList(item: any) {
    if (!this.wishlistService.productInWishList(item)) {
      this.showSuccess("Thêm yêu thích thành công!")
      this.wishlistService.addToWishList(item);
    }
  }

  getListNewest() {
    this.blogService.getListNewest(3).subscribe({
      next: res => {
        this.listBlogNewest = res;
      }, error: err => {
        console.log(err);
      }
    })
  }

  showSuccess(text: string) {
    this.messageService.add({severity: 'success', summary: 'Thông Báo', detail: text});
  }

  showError(text: string) {
    this.messageService.add({severity: 'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity: 'warn', summary: 'Cảnh Báo', detail: text});
  }

  scrollToTop() {
    const scrollDuration = 1000; // Thời gian để cuộn lên (1 giây)
    const scrollStep = -window.scrollY / (scrollDuration / 15);

    const scrollInterval = setInterval(function () {
      if (window.scrollY != 0) {
        window.scrollBy(0, scrollStep);
      } else {
        clearInterval(scrollInterval);
      }
    }, 15);
  }
}
