import { Component } from '@angular/core';
import { ProductService } from 'src/app/_service/product.service';
import { OrderService } from 'src/app/_service/order.service';
import { BlogService } from 'src/app/_service/blog.service';
import { faCalculator, faDollarSign, faComment, faClipboard ,faFile} from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent {
  totalProducts: number = 0;
  totalProductquantity:number=0;
  totalRevenue: number = 0;
  totalOrder: number=0;
  totalblog: number=0;
  cacli=faCalculator;
  clip=faClipboard;
  dolo=faDollarSign;
  cmt=faComment;
  faf=faFile;
  data: any;
  options: any;
  constructor(private productService: ProductService,private orderService: OrderService,private blogService: BlogService) {}

  ngOnInit(): void {

    
    //gọi ra api tổng sản phẩm
    this.productService.getTotalProducts().subscribe(total => {
      this.totalProducts = total;
    });
    //gọi ra api tổng sản phẩm đã bán
    this.productService.getTotalProductQuantity().subscribe(total => {
        this.totalProductquantity = total;
      });
    // gọi tổng doanhthu
    this.orderService.getTotalRevenue().subscribe(revenue => {
      this.totalRevenue = revenue;
    });
      // gọi tổng đơn hàng
      this.orderService.getOders().subscribe(odrder => {
        this.totalOrder = odrder;
      });
      // gọi tổng blog
      this.blogService.getBlogtt().subscribe(blogss => {
        this.totalblog = blogss;
      });
     

      const documentStyle = getComputedStyle(document.documentElement);
      const textColor = documentStyle.getPropertyValue('--text-color');
      const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
      const surfaceBorder = documentStyle.getPropertyValue('--surface-border');
      
    // this.data = {
    //     labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
    //     datasets: [
    //         {
    //             label: 'Sản Phẩm',
    //             fill: false,
    //             borderColor: documentStyle.getPropertyValue('--blue-500'),
    //             yAxisID: 'y',
    //             tension: 0.4,
    //             data: [65, 59, 80, 81, 56, 55, 10, 1, 56, 55, 10,50]
    //         },
    //         {
    //             label: 'Doanh Thu',
    //             fill: false,
    //             borderColor: documentStyle.getPropertyValue('--green-500'),
    //             yAxisID: 'y1',
    //             tension: 0.4,
    //             data: [28, 48, 40, 19, 86, 27, 90, 19, 48, 40, 19,90]
    //         }
    //     ]
    // };

    this.orderService.getListOrdercharts(12).subscribe((orders: any[]) => {
      // Khởi tạo mảng để lưu dữ liệu từ API
      const monthYears: string[] = [];
      const totalRevenues: number[] = [];
      

      // Lặp qua mỗi đối tượng trong mảng orders và lấy các giá trị monthYear và totalRevenue
      orders.forEach(order => {
        monthYears.push(order.monthYear);
        totalRevenues.push(order.totalRevenue);
      });

    // 3. Gán dữ liệu cho biểu đồ
    this.data = {
        labels: monthYears,
        datasets: [
            {
                label: 'Doanh thu',
                fill: false,
                borderColor: documentStyle.getPropertyValue('--blue-500'),
                yAxisID: 'y',
                tension: 0.6,
                data: totalRevenues
            }
        ]
    };
    this.options = {
      maintainAspectRatio: false,
      aspectRatio: 0.8,
      plugins: {
          legend: {
              labels: {
                  color: textColor
              }
          }
      },
      scales: {
          x: {
              ticks: {
                  color: textColorSecondary,
                  font: {
                      weight: 500
                  }
              },
              grid: {
                  color: surfaceBorder,
                  drawBorder: false
              }
          },
          y: {
              ticks: {
                  color: textColorSecondary
              },
              grid: {
                  color: surfaceBorder,
                  drawBorder: false
              }
          }

      }
  };
  });
}  
  
}
