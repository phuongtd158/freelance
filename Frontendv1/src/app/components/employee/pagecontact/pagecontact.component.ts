import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { CategoryService } from 'src/app/_service/category.service';
import { ContactService } from 'src/app/_service/contact.service';

@Component({
  selector: 'app-pagecontact',
  templateUrl: './pagecontact.component.html',
  styleUrls: ['./pagecontact.component.css'],
  
  providers: [MessageService]
})
export class PagecontactComponent implements OnInit {

  listCategory : any;

  displayForm: boolean = false;

  deleteForm : boolean = false;

  onUpdate : boolean = false;

  categoryForm : any ={
    id: null,
    name : null,
    gmail:null,
    content:null
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
  constructor(private messageService : MessageService,
    private contactService:ContactService,
    private categoryService: CategoryService){}

  ngOnInit(): void {
    this.getListCategory();
  }


  getListCategory(){
    this.contactService.getList().subscribe({
      next: res =>{
        this.listCategory = res;
        console.log(res);
      },error: err =>{
        console.log(err);
      }
    })
  }

  showForm(){
    this.onUpdate = false;
    this.categoryForm ={
      id : null,
      name : null,
      gmail:null,
      content:null
    }
    this.displayForm = true;
  }

  
 onUpdateForm(id: number,name : string,gmail: string,content: string){
      this.onUpdate = true;
      this.displayForm =true;
      this.categoryForm.id = id;
      this.categoryForm.name = name;
      this.categoryForm.gmail = gmail;
      this.categoryForm.content = content;
  }

  onDelete(id:number,name : string,gmail: string,content: string){
    this.deleteForm = true;
    this.categoryForm.id = id;
    this.categoryForm.name = name;    
    this.categoryForm.gmail = gmail;
    this.categoryForm.content = content;
  }


  updateContact(){
    const {id,name,gmail,content} = this.categoryForm;
    this.contactService.updateContact(id,name,gmail,content).subscribe({
      next: res =>{
        this.getListCategory();
        this.showSuccess("Cập nhật danh mục thành công!");
        this.displayForm = false;
      },error: err =>{
        this.showError(err.message);
      }
    })
  }


  enableCategory(id : number){
    this.contactService.enableContact(id).subscribe({
      next: res =>{
        this.getListCategory();
        this.showSuccess("Cập nhật thành công!!");
      },error: err=>{
        this.showError(err.message);
      }
    })
  }


  deleteCategory(){
    const {id} = this.categoryForm;
    this.contactService.deleleContact(id).subscribe({
      next: res =>{
        this.getListCategory();
        this.showWarn("Xóa danh mục thành công!!");
        this.deleteForm = false;
      },error: err=>{
        this.showError(err.message);
      }
    })
  }

  showSuccess(text: string) {
    this.messageService.add({severity:'success', summary: 'Success', detail: text});
  }
  showError(text: string) {
    this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }

  showWarn(text : string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }
}
