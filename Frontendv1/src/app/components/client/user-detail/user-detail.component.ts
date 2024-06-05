import {Component, OnInit} from '@angular/core';
import {StorageService} from 'src/app/_service/storage.service';
import {UserService} from 'src/app/_service/user.service';
import {MessageService} from 'primeng/api';
import {ShippingService} from "../../../_service/shipping.service";

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrls: ['./user-detail.component.css']
})
export class UserDetailComponent implements OnInit {

  username: any;
  user: any;

  changePassword: boolean = false;

  updateForm: any = {
    firstname: null,
    lastname: null,
    email: null,
    country: null,
    address: null,
    phone: null,
    town: null,
    state: null,
    ward: null
  }

  provinces!: any[];
  districts!: any[];
  wards!: any[];

  selectedProvince: any;
  selectedDistrict: any;
  selectedWard: any;

  changePasswordForm: any = {
    oldPassword: null,
    newPassword: null
  }

  constructor(private storageService: StorageService, private userService: UserService, private messageService: MessageService, private shippingService: ShippingService) {
  }

  async ngOnInit(): Promise<void> {
    this.username = this.storageService.getUser().username;
    // console.log(this.storageService.getUser())
    await this.getProvince()
    this.getUser();
    console.log(this.getUser);
  }

  getUser() {
    this.userService.getUser(this.username).subscribe({
      next: async (res) => {
        this.user = res;
        this.updateForm.firstname = res.firstname;
        this.updateForm.lastname = res.lastname;
        this.updateForm.email = res.email;
        this.updateForm.country = res.country;
        this.updateForm.address = res.address;
        this.updateForm.phone = res.phone;
        if (res.town) {
          this.selectedProvince = this.provinces.find((item: any) => item.ProvinceName === res.town)
          await this.getDistrict()
          this.selectedDistrict = this.districts.find((item: any) => item.DistrictName === res.state)
          await this.getWard()
          this.selectedWard = this.wards.find((item: any) => item.WardName === res.ward)
          this.setAddress()
        }
      }, error: err => {
        console.log(err);
      }
    })
  }

  isNullOrEmpty(val: string): boolean {
    return val === null || val === undefined || val === ''
  }

  validInformation() {
    const ignoreKey = ['country'] // Các trường không bắt buộc
    for (const key in this.updateForm) {
      const value = this.updateForm[key]
      if (!ignoreKey.includes(key)) {
        if (this.isNullOrEmpty(value)) {
          this.showWarn('Vui lòng nhập đầy đủ thông tin!')
          return false
        }
        if (key === 'phone' && !value?.trim().match("^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$")) {
          this.showWarn('Số điện thoại không đúng định dạng!')
          return false
        }
        if (key === 'email' && !value?.trim().match("^[a-zA-Z_0-9._%+-]+@[a-zA-Z_0-9.-]+\\.[a-zA-Z_]{2,}$")) {
          this.showWarn('Email không đúng định dạng!')
          return false
        }
      }
    }
    return true
  }

  updateProfile() {
    if (!this.validInformation()) return
    const {firstname, lastname, email, country, state, address, phone, town, ward} = this.updateForm;
    this.userService.updateProfile(this.username, firstname, lastname, email, country, state, address, phone, town, ward)
      .subscribe({
        next: res => {
          // alert("Cập nhật thông tin thành công")
          this.showSuccess("Cập nhật thông tin thành công");
          this.getUser();

        }, error: err => {
          console.log(err);
        }
      })
  }

  changePasswordFunc() {
    const {oldPassword, newPassword} = this.changePasswordForm;
    this.userService.changePassword(this.username, oldPassword, newPassword).subscribe({
      next: res => {
        this.getUser();
        this.showSuccess("Thay đổi mật khẩu thành công !");
      }, error: err => {
        console.log(err);
      }
    })
  }


  showChangePassword() {
    this.changePassword = true;
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

  async getProvince() {
    const res: any = await this.shippingService.getProvince().toPromise()
    this.provinces = res.data;
  }

  async getDistrict() {
    this.resetDistrictAndWard()
    console.log(this.selectedProvince)
    if (this.selectedProvince) {
      this.updateForm.town = this.selectedProvince.ProvinceName
      const res: any = await this.shippingService.getDistrict(this.selectedProvince.ProvinceID).toPromise()
      this.districts = res.data;
    }
  }

  async getWard() {
    this.resetWard()
    if (this.selectedDistrict) {
      this.updateForm.state = this.selectedDistrict.DistrictName
      const res: any = await this.shippingService.getWard(this.selectedDistrict.DistrictID).toPromise()
      this.wards = res.data;
    }
  }

  resetDistrictAndWard() {
    this.selectedDistrict = null
    this.selectedWard = null
    this.districts = [];
    this.wards = [];
    this.setAddress()
  }

  resetWard() {
    this.selectedWard = null;
    this.wards = [];
    this.setAddress()
  }

  setAddress() {
    if (this.selectedWard) {
      this.updateForm.ward = this.selectedWard.WardName
      this.updateForm.address = `${this.selectedWard.WardName}, ${this.selectedDistrict.DistrictName}, ${this.selectedProvince.ProvinceName}`
    } else {
      this.updateForm.address = ''
    }
  }


}
