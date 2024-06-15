import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { OrderService } from 'src/app/_service/order.service';
import { StorageService } from 'src/app/_service/storage.service';
import { UploadCloudinaryService } from "../../../_service/upload-cloudinary.service";
import { ORDER_STATUS, REASON_OPTIONS } from "../../../share/constants/constants";

@Component({
  selector: 'app-my-order',
  templateUrl: './my-order.component.html',
  styleUrls: ['./my-order.component.css']
})
export class MyOrderComponent implements OnInit {


  listOrder: any;
  username: any;
  visibleDialog: boolean = false;
  order!: any;
  type!: any;
  ORDER_STATUS = ORDER_STATUS

  returnForm: any = {
    reason: null,
    urlImg: ''
  }
  reasonOptions = REASON_OPTIONS

  fileUploadImage: any[] = [];
  urlImage!: any;

  transform(status: number): string {
    switch (status) {
      case 0:
        return 'Chờ Xác Nhận';
      case 1:
        return 'Chờ Lấy Hàng';
      case 2:
        return 'Chờ Giao Hàng';
      case 3:
        return 'Đã Giao';
      case 4:
        return 'Đã Hủy';
      case 5:
        return 'Trả Hàng';
      default:
        return 'Lỗi Hệ Thống';
    }
  }

  transform1(status: number): string {
    switch (status) {
      case 0:
        return 'Thanh toán khi nhận hàng';
      case 1:
        return 'Ví VNPAY';
      default:
        return '';
    }
  }

  constructor(private orderService: OrderService, private storageService: StorageService, private messageService: MessageService, private uploadService: UploadCloudinaryService) {
  }

  ngOnInit(): void {
    this.username = this.storageService.getUser().username;
    this.getListOrder();
  }


  getListOrder() {
    this.orderService.getListOrderByUser(this.username).subscribe({
      next: res => {
        this.listOrder = res;
        console.log(this.listOrder);
      }, error: err => {
        console.log(err);
      }
    })
  }

  downloadExcel(id: number) {
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

  showDialog(order: any, type: 'CANCEL' | 'CONFIRM' | 'RETURNS') {
    this.visibleDialog = true
    this.order = order
    this.type = type
  }

  visibleDialogReturn: boolean = false;

  showDialogReturn(order: any) {
    this.order = order
    this.visibleDialogReturn = true
  }

  closeDialogReturn() {
    this.visibleDialogReturn = false
    this.order = null
  }

  closeDialog() {
    this.visibleDialog = false
    this.order = null
  }

  handleConfirmOrCancel() {
    const id = this.order.id
    const status = this.type === 'CANCEL' ? '4' : '9'
    const message = this.type = 'CANCEL' ? 'Huỷ' : 'Xác nhận';
    this.orderService.updateOrder(id, status).subscribe((res) => {
      if (res.id) {
        this.showSuccess(`${message} đơn hàng thành công !`)
        this.closeDialog()
        this.getListOrder()
      } else {
        this.showError(`${message} đơn hàng thất bại !`)
      }
    })
  }

  async handleReturn() {
    if (!this.returnForm.reason) {
      this.showWarn(`Vui lòng nhập lý do hoàn trả !`)
      return
    }
    if (!this.fileUploadImage.length && this.fileUploadImage.length <= 0) {
      this.showWarn(`Vui lòng chọn hình ảnh !`)
      return
    }
    const formData = new FormData();
    for (let i = 0; i < this.fileUploadImage.length; i++) {
      formData.append('files', this.fileUploadImage[i]);
    }
    if (this.fileUploadImage.length > 0) {
      this.urlImage = await this.uploadService.upload(formData).toPromise();
    }
    this.returnForm.urlImg = Array.from(this.urlImage).join(";")
    const params = {
      urlImg: this.returnForm.urlImg,
      reason: this.returnForm.reason,
      status: 10
    }
    this.orderService.returnOrder(this.order.id, params).subscribe((res) => {
      if (res.id) {
        this.showSuccess(`Yêu cầu trả hàng thành công !`)
        this.closeDialogReturn()
        this.getListOrder()
      } else {
        this.showError(`Yêu cầu trả hàng thất bại !`)
      }
    })
  }

  onUploadImage($event: any) {
    this.fileUploadImage.push(...$event.addedFiles);
  }

  onRemoveImage(f: any) {
    this.fileUploadImage.splice(this.fileUploadImage.indexOf(f), 1);
  }

  showSuccess(text: string) {
    this.messageService.add({severity: 'success', summary: 'Thông báo', detail: text});
  }

  showError(text: string) {
    this.messageService.add({severity: 'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity: 'warn', summary: 'Thông báo', detail: text});
  }

}
