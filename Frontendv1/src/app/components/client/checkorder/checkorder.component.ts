import { Component } from '@angular/core';
import { OrderService } from 'src/app/_service/order.service';

@Component({
  selector: 'app-checkorder',
  templateUrl: './checkorder.component.html',
  styleUrls: ['./checkorder.component.css']
})
export class CheckorderComponent {
  listOrder: any; // Initialize listOrder array
  orderCode:any;
  constructor(private orderService: OrderService) { } // Inject OrderService

  
  // Method to search orders by order code
    searchOrder(event: any): void {
      const orderCode = event.target.value;
      // Rest of the code remains unchanged
      this.orderService.checkoutOrder(orderCode).subscribe(
          (response: any) => {
              this.listOrder = [response];
          },
          (error: any) => {
              console.error('Error searching order:', error);
              this.listOrder = [];
          }
      );
    }
    downloadExcel(id : number) {
      this.orderService.downloadExcel(id).subscribe(
        (response: Blob) => {
          // Tạo một URL tạm thời cho file blob
          const url = window.URL.createObjectURL(new Blob([response]));
          // Tạo một link tải xuống và kích hoạt nó
          const link = document.createElement('a');
          link.href = url;
          link.download = 'orders.xlsx';
          link.click();
  
          // Giải phóng URL tạm thời
          window.URL.revokeObjectURL(url);
        },
        error => {
          console.error('Error downloading Excel:', error);
        }
      );
    }
    transform1(status: number): string {
      switch (status) {
        case 0: return 'Thanh toán khi nhận hàng';
        case 1: return 'Ví VNPAY';
        default: return '';
      }
    }

}