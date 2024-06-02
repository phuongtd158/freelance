import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/_service/storage.service';
import { UserService } from 'src/app/_service/user.service';
import { SettingService } from 'src/app/_service/setting.service';

@Component({
  selector: 'app-setting',
  templateUrl: './setting.component.html',
  styleUrls: ['./setting.component.css']
})
export class SettingComponent implements OnInit {

  username: any;
  setting :any;

  

  updateForm: any ={
    gmail: null,
    phone: null,
    logo: null,
    facebook: null,
    youtube:null,
    telegram: null,
    instagram: null
  }

 

  constructor(private storageService: StorageService,
    private settingService: SettingService){}

  ngOnInit(): void {
   
    this.getListSetting();
  }

  getListSetting(){
  
    this.settingService.getListSetting().subscribe({
      next: res=>{
        
        this.setting = res[0];
        this.updateForm.gmail = res[0].gmail;
        this.updateForm.phone = res[0].phone;
        this.updateForm.logo = res[0].logo;
        this.updateForm.facebook = res[0].facebook;
        this.updateForm.youtube = res[0].youtube;
        this.updateForm.telegram = res[0].telegram;
        this.updateForm.instagram = res[0].instagram;
        this.updateForm.diachi = res[0].diachi;
      },error : err =>{
        console.log(err);
      }
    })
  }

  updateProfile(){
    const{gmail,phone,logo,facebook,youtube,telegram,instagram,diachi} = this.updateForm;
    this.settingService.updateSetting(gmail,phone,logo,facebook,youtube,telegram,instagram,diachi).subscribe({
      next: res =>{
        alert(" Cập nhật thông tin thành công")
        this.getListSetting();
        
      },error: err=>{
        console.log(err);
      }
    })
  }


}
