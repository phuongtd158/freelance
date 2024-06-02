import { Component } from '@angular/core';
import { OrderService } from 'src/app/_service/order.service';
@Component({
  selector: 'app-error-message',
  templateUrl: './error-message.component.html',
  styleUrls: ['./error-message.component.css']
})
export class ErrorMessageComponent {
  // liststatus:any;
  // constructor(private orderService: OrderService) { }

  // ngOnInit(): void {
  //   // Thiết lập mặc định trạng thái đơn hàng là 4 khi component được khởi tạo
  //   this.orderService.updateOrderstatus();
  // }
  // updateProduct() {
  //   // Gọi phương thức updateOrderstatus() của OrderService
  //   this.orderService.updateOrderstatus().subscribe({
  //     next: res => {
  //       // Xử lý khi cập nhật thành công
  //     },
  //     error: err => {
  //       // Xử lý khi gặp lỗi
  //     }
  //   });
  // }
}
