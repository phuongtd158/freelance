import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'currencyVND'
})
export class CurrencyVNDPipe implements PipeTransform {

  transform(value: any, ...args: unknown[]): unknown {
    if (value == null) return '';
    // Chuyển đổi value thành số nếu nó là chuỗi
    let amount: number = typeof value === 'string' ? parseFloat(value) : value;
    // Kiểm tra nếu giá trị không phải là số
    if (isNaN(amount)) return '';
    // Định dạng số với dấu phẩy hàng ngàn
    let formattedAmount = amount.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    // Thêm đuôi ' VNĐ'
    return `${formattedAmount} VNĐ`;
  }

}
