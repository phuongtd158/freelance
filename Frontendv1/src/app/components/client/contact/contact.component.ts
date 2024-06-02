import { Component } from '@angular/core';
import {faBars, faHeart, faEnvelope ,faMobile,faAddressBook, faTimeline,faUser} from '@fortawesome/free-solid-svg-icons'
import { SettingService } from 'src/app/_service/setting.service';
import { ContactService } from 'src/app/_service/contact.service';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent {
  icon_mail_alt = faEnvelope;
  phone=faMobile;
  add=faAddressBook;
  time=faTimeline;
  setchung:any;
  updateForm: any ={
    name: null,
    gmail: null,
    content: null
  }
 

  // Method to check if input contains '@gmail.com'
  checkForGmail() {
    return this.updateForm.gmail.includes('@gmail.com');
  }

  // NgxSummernote
  config = {
    placeholder: '',
    tabsize: 2,
    height: 200, // Change the type from string to number
    uploadImagePath: '/api/upload',
    toolbar: [
      ['misc', ['codeview', 'undo', 'redo']],
      ['style', ['bold', 'italic', 'underline', 'clear']],
      ['font', ['bold', 'italic', 'underline', 'strikethrough', 'superscript', 'subscript', 'clear']],
      ['fontsize', ['fontname', 'fontsize', 'color']],
      ['para', ['style', 'ul', 'ol', 'paragraph', 'height']],
      ['insert', ['table', 'picture', 'link', 'video', 'hr']]
    ],
    fontNames: ['Helvetica', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'Roboto', 'Times']
  };
  ngOnInit(): void {
    this.getListSetting();
  }
  constructor (
    private settingService:SettingService,
    private contactService:ContactService,
    private messageService:MessageService
  ){}
  getListSetting(){
  
    this.settingService.getListSetting().subscribe({
      next: res =>{
        this.setchung= res[0];
        // console.log(this.setchung.gmail);
      },error: err =>{
        console.log(err);
      }
    })
  }
  // createContact(){
  //   const{name,gmail,content} = this.updateForm;
  //   this.contactService.createContact(name,gmail,content).subscribe({
  //     next: res =>{
  //       // alert("Cập nhật thông tin thành công")
  //       this.showSuccess("Gửi Liên Hệ Thành Công");        
  //     },error: err=>{
  //       console.log(err);
  //     }
  //   })
  // }
  createContact() {
    
    const { name, gmail, content } = this.updateForm;

    // Kiểm tra xem các trường không được để trống
    if (name.trim() === '' || gmail.trim() === '' || content.trim() === '') {
        // Hiển thị cảnh báo ngay tại chỗ
        this.showWarn("Tên, gmail, hoặc nội dung không được để trống.");
        return; // Ngăn việc tiếp tục thực hiện hàm
    }

    // Nếu không có trường nào để trống, tiếp tục xử lý
    this.contactService.createContact(name, gmail, content).subscribe({
        next: res => {
            // alert("Cập nhật thông tin thành công")
            this.showSuccess("Gửi Liên Hệ Thành Công");
        },
        error: err => {
            console.log(err);
        }
    });
  }



  showSuccess(text: string) {
    this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
    this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text: string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }
}
