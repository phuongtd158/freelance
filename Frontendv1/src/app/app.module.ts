import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';


import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DashboardComponent} from './components/admin/dashboard/dashboard.component';
import {CategoryComponent} from './components/admin/category/category.component';
import {ProductComponent} from './components/admin/product/product.component';
import {OrderComponent} from './components/admin/order/order.component';
import {BlogComponent} from './components/admin/blog/blog.component';
import {AccountComponent} from './components/admin/account/account.component';
import {IndexComponent} from './components/client/index/index.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CardModule} from 'primeng/card';
import {DialogModule} from 'primeng/dialog';
import {ToastModule} from 'primeng/toast';
import {ButtonModule} from 'primeng/button';
import {TableModule} from 'primeng/table';
import {InputNumberModule} from 'primeng/inputnumber';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToolbarModule} from 'primeng/toolbar';
import {FileUploadModule} from 'primeng/fileupload';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {InputTextModule} from 'primeng/inputtext';
import {RadioButtonModule} from 'primeng/radiobutton';
import {DividerModule} from 'primeng/divider';
import {CarouselModule} from 'primeng/carousel';
import {HomeComponent} from './components/client/home/home.component';
import {OverlayPanelModule} from 'primeng/overlaypanel';
import {ShopComponent} from './components/client/shop/shop.component';
import {ProductDetailComponent} from './components/client/product-detail/product-detail.component';
import {CartComponent} from './components/client/cart/cart.component';
import {CheckoutComponent} from './components/client/checkout/checkout.component';
import {TabViewModule} from 'primeng/tabview';
import {PasswordModule} from 'primeng/password';
import {SliderModule} from 'primeng/slider';
import {DataViewModule} from 'primeng/dataview';
import {TagComponent} from './components/admin/tag/tag.component';
import {MultiSelectModule} from 'primeng/multiselect';
import {BlogClientComponent} from './components/client/blog-client/blog-client.component';
import {BlogDetailComponent} from './components/client/blog-detail/blog-detail.component';
import {UserDetailComponent} from './components/client/user-detail/user-detail.component';
import {MyOrderComponent} from './components/client/my-order/my-order.component';
import {SearchComponent} from './components/client/search/search.component';
import {LoginPageComponent} from './components/client/login-page/login-page.component';
import {ChartsComponent} from './components/admin/charts/charts.component';
import {EmailVerificationComponent} from './components/client/email-verification/email-verification.component';
import {ChitietComponent} from './components/admin/chitiet/chitiet.component';
import {OrderchitietComponent} from './components/client/orderchitiet/orderchitiet.component';
import {ContactComponent} from './components/client/contact/contact.component';
import {NgxSummernoteModule} from 'ngx-summernote';
import {CheckvnpayComponent} from './components/client/checkvnpay/checkvnpay.component';
import {ErrorMessageComponent} from './components/client/error-message/error-message.component';
import {ChartModule} from 'primeng/chart';
import {ProgressSpinnerModule} from 'primeng/progressspinner';
import {SanphamComponent} from './components/client/sanpham/sanpham.component';
import {TabMenuModule} from 'primeng/tabmenu';
import {UserComponent} from './components/admin/user/user.component';
import {InputSwitchModule} from 'primeng/inputswitch';
import {UserroleComponent} from './components/admin/userrole/userrole.component';
import {StatusorderComponent} from './components/admin/statusorder/statusorder.component';
import {VoucherComponent} from './components/admin/voucher/voucher.component';
import {PagemanagementComponent} from './components/moderator/pagemanagement/pagemanagement.component';
import {PagesanphamComponent} from './components/moderator/pagesanpham/pagesanpham.component';
import {PagebaivietComponent} from './components/moderator/pagebaiviet/pagebaiviet.component';
import {GiamgiaproductComponent} from './components/admin/giamgiaproduct/giamgiaproduct.component';
import {ProductcolorComponent} from './components/admin/productcolor/productcolor.component';
import {ProductsizeComponent} from './components/admin/productsize/productsize.component';
import {ProductroomComponent} from './components/admin/productroom/productroom.component';
import {ImageComponent} from './components/admin/image/image.component';
import {PagecolorComponent} from './components/moderator/pagecolor/pagecolor.component';
import {PagesizeComponent} from './components/moderator/pagesize/pagesize.component';
import {PageroomComponent} from './components/moderator/pageroom/pageroom.component';
import {SettingComponent} from './components/admin/setting/setting.component';
import {GioithieuComponent} from './components/client/gioithieu/gioithieu.component';
import {AboutComponent} from './components/admin/about/about.component';
import {PolicyComponent} from './components/admin/policy/policy.component';
import {PolicyClientComponent} from './components/client/policy-client/policy-client.component';
import {PolicyDetailComponent} from './components/client/policy-detail/policy-detail.component';
import {AdcontactComponent} from './components/admin/adcontact/adcontact.component';
import {CheckorderComponent} from './components/client/checkorder/checkorder.component';
import {PagecontactComponent} from './components/employee/pagecontact/pagecontact.component';
import {PageloyoutComponent} from './components/employee/pageloyout/pageloyout.component';
import {OrdereditComponent} from './components/moderator/orderedit/orderedit.component';
import {httpInterceptorProviders} from "./_helpers/http.interceptor";
import {DropdownModule} from "primeng/dropdown";
import {NumberOnlyDirective} from "./share/directive/number-only.directive";
import {DatePipe} from '@angular/common';
import {CalendarModule} from "primeng/calendar";
import {NgxDropzoneModule} from "ngx-dropzone";


@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    CategoryComponent,
    ProductComponent,
    OrderComponent,
    BlogComponent,
    AccountComponent,
    IndexComponent,
    HomeComponent,
    CartComponent,
    CheckoutComponent,
    ShopComponent,
    ProductDetailComponent,
    TagComponent,
    BlogClientComponent,
    BlogDetailComponent,
    UserDetailComponent,
    MyOrderComponent,
    SearchComponent,
    LoginPageComponent,
    ChartsComponent,
    EmailVerificationComponent,
    ChitietComponent,
    OrderchitietComponent,
    ContactComponent,
    CheckvnpayComponent,
    ErrorMessageComponent,
    SanphamComponent,
    UserComponent,
    UserroleComponent,
    StatusorderComponent,
    VoucherComponent,
    PagemanagementComponent,
    PagesanphamComponent,
    PagebaivietComponent,
    GiamgiaproductComponent,
    ProductcolorComponent,
    ProductsizeComponent,
    ProductroomComponent,
    ImageComponent,
    PagecolorComponent,
    PagesizeComponent,
    PageroomComponent,
    SettingComponent,
    GioithieuComponent,
    AboutComponent,
    PolicyComponent,
    PolicyClientComponent,
    PolicyDetailComponent,
    AdcontactComponent,
    CheckorderComponent,
    PagecontactComponent,
    PageloyoutComponent,
    OrdereditComponent,
    NumberOnlyDirective
  ],
  exports: [
    NumberOnlyDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FontAwesomeModule,
    NgbModule,
    CardModule,
    DialogModule,
    ToastModule,
    ButtonModule,
    TableModule,
    InputNumberModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToolbarModule,
    FileUploadModule,
    ConfirmDialogModule,
    InputTextareaModule,
    InputTextModule,
    RadioButtonModule,
    DividerModule,
    CarouselModule,
    OverlayPanelModule,
    TabViewModule,
    PasswordModule,
    SliderModule,
    DataViewModule,
    MultiSelectModule,
    NgxSummernoteModule,
    ChartModule,
    ProgressSpinnerModule,
    TabMenuModule,
    InputSwitchModule,
    DropdownModule,
    CalendarModule,
    NgxDropzoneModule
  ],
  providers: [httpInterceptorProviders, DatePipe],

  bootstrap: [AppComponent]
})
export class AppModule {
}
