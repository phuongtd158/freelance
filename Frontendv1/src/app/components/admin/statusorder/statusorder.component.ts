import {OrderService} from 'src/app/_service/order.service';
import {Component, OnInit} from '@angular/core';
import {ConfirmationService, MessageService} from 'primeng/api';
import {ORDER_STATUS, REASON_OPTIONS, ROLE} from "../../../share/constants/constants";
import {StorageService} from "../../../_service/storage.service";

@Component({
  selector: 'app-statusorder',
  templateUrl: './statusorder.component.html',
  styleUrls: ['./statusorder.component.css'],
  providers: [MessageService, ConfirmationService]
})
export class StatusorderComponent implements OnInit {
  onUpdate: boolean = false;
  liststatus: any;
  showForm: boolean = false;
  showFormReturn: boolean = false;
  productForm: any = {
    // name : null,
    status: null
  };
  currentStatusCode: any = null
  listOrder: any;
  order: any = null;
  reason: string = ''
  imgUrl: string[] = []
  // transform(status: number): string {
  //   switch (status) {
  //     case 0: return 'Chờ Xác Nhận';
  //     case 1: return 'Chờ Lấy Hàng';
  //     case 2: return 'Chờ Giao Hàng';
  //     case 3: return 'Đã Giao';
  //     case 4: return 'Đã Hủy';
  //     case 5: return 'Trả Hàng';
  //     default: return 'Lỗi Hệ Thống';
  //   }
  // }
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

  ORDER_STATUS = ORDER_STATUS
  reasonOptions = REASON_OPTIONS
  routerToOrderDetail: string = '/admin/chitiet'
  visibleDialogConfirm: boolean = false

  constructor(private orderService: OrderService, private messageService: MessageService, private storageService: StorageService) {
  }

  ngOnInit(): void {
    this.getListOrder();
    // this.getListStatus();
    if (this.storageService.isLoggedIn()) {
      const roles: string[] = this.storageService.getUser().roles;
      if (roles.includes(ROLE.MODERATOR)) {
        this.routerToOrderDetail = '/moderator/chitiet'
      } else if (roles.includes(ROLE.EMPLOYEE)) {
        this.routerToOrderDetail = '/employee/chitiet'
      }
    }
  }

  getListStatus(currentStatusCode: string) {
    this.orderService.getListstatus(this.currentStatusCode).subscribe({
      next: res => {
        this.liststatus = res;
        this.liststatus = this.liststatus.filter((item: any) => item.code !== ORDER_STATUS.ERROR)
      }, error: err => {
        console.log(err);
      }
    })
  }

  getListOrder() {
    this.orderService.getListOrder().subscribe({
      next: res => {
        this.listOrder = res;
        console.log(this.listOrder);
      }, error: err => {
        console.log(err);
      }
    })
  }

  openUpdate(data: any) {
    this.onUpdate = true;
    this.showForm = true;
    this.productForm.id = data.id;
    this.productForm.orderCode = data.orderCode;
    this.productForm.status = data.orderstatus.id;
    this.currentStatusCode = data.orderstatus.code;
    this.getListStatus(this.currentStatusCode)
  }

  showDialogReturn(order: any) {
    this.order = order
    this.reason = order.reason
    this.imgUrl = String(order.urlImg)?.split(';')
    this.showFormReturn = true
  }

  showDialogConfirm(order: any) {
    this.order = order
    this.visibleDialogConfirm = true
  }

  closeDialogConfirm() {
    this.order = null
    this.visibleDialogConfirm = false
  }

  handleConfirm() {
    const id = this.order.id
    this.orderService.updateOrder(id, '9').subscribe((res) => {
      if (res.id) {
        this.showSuccess(`Xác nhận đơn hàng thành công !`)
        this.closeDialogConfirm()
        this.getListOrder()
      } else {
        this.showError(`Xác nhận đơn hàng thất bại !`)
      }
    })
  }

  closeDialogReturn() {
    this.order = null
    this.reason = ''
    this.imgUrl = []
    this.showFormReturn = false
  }

  updateProduct() {
    const {id, status} = this.productForm;
    console.log(this.productForm);
    this.orderService.updateOrder(id, status).subscribe({
      next: res => {
        this.getListOrder();
        this.showForm = false;
        this.showSuccess("Cập nhật thành công");
      }, error: err => {
        this.showError(err.message);
      }
    })
  }

  handleReturn(type: 'CONFIRM' | 'REJECT') {
    const id = this.order.id;
    const status = type === 'CONFIRM' ? '5' : '11'
    const mess = type === 'CONFIRM' ? 'Xác nhận' : 'Từ chối'
    this.orderService.updateOrder(id, status).subscribe({
      next: res => {
        this.getListOrder();
        this.closeDialogReturn()
        this.showSuccess(`${mess} trả hàng thành công`);
      }, error: err => {
        this.showError(err.message);
      }
    })
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
