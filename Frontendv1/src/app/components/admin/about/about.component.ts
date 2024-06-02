import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { BlogService } from 'src/app/_service/blog.service';
import { ImageService } from 'src/app/_service/image.service';
import { StorageService } from 'src/app/_service/storage.service';
import { TagService } from 'src/app/_service/tag.service';
import { AboutService } from 'src/app/_service/about.service';


@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css'],
  providers: [MessageService]
})
export class AboutComponent  implements OnInit {

  listBlog : any;
  listTag : any[] =[];
  listImage: any;
  username : any;
  
  onUpdate : boolean =false;
  showForm : boolean = false;
  showImage: boolean = false;
  onDelete: boolean = false;
  imageChoosen : any;
  image: any;
  disabled : boolean = true;

  selectedFiles ?: FileList;
  currentFile ?: File;


  blogForm : any = {
    id: null,
    title: null,
    noidung: null,
    imageId : null,
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
  // đặt chuỗi thành ...
  truncateContent(content: string, maxLength: number): string {
    if (content.length <= maxLength) {
        return content;
    } else {
        return content.substring(0, maxLength) + '(....)';
    }
  }
  




  constructor(private blogService: BlogService,private aboutService:AboutService,
    private storageService: StorageService,private tagService: TagService,private imageService: ImageService,private messageService: MessageService){

  }

  ngOnInit(): void {
    this.username = this.storageService.getUser().username;
    this.getList();
    this.getListImage();
  }


  uploadFile(event: any){
    this.selectedFiles = event.target.files;
    if(this.selectedFiles){
      const file: File | null = this.selectedFiles.item(0);
      if(file){
        this.currentFile = file;
        this.imageService.upload(this.currentFile).subscribe({
          next: res =>{
            this.currentFile = undefined;
            this.getListImage();
          },error: err=>{
          }
        })
      }
      this.currentFile = undefined;
    }
  }

  showNew(){
    this.onUpdate = false;
    this.showForm = true;
    this.image = null;
    this.blogForm = {
      id: null,
      title: null,
      noidung: null,
      imageId : null,
    }
  }
  showUpdate(data: any){
    this.onUpdate = true;
    this.showForm = true;
    this.blogForm.id = data.id;
    this.blogForm.title = data.title;
    this.blogForm.noidung = data.noidung;
    this.image = data.image;
    
  }

  showDelete(id: number, title: string){
    this.onDelete=true;
    this.blogForm.id = id;
    this.blogForm.title = title;
  }


  onChooseImage(){
    this.showImage =true;
    this.disabled = true;
    let data = document.querySelectorAll('.list-image img');
      data.forEach(i =>{
        i.classList.remove('choosen');
    })  
  }

  getListImage(){
    this.imageService.getList().subscribe({
      next:res=>{
        this.listImage =res;
      },error: err=>{
        console.log(err);
      }
    })
  }


  getList(){
    this.aboutService.getListAbout().subscribe({
      next: res =>{
        this.listBlog =res;
      },error: err =>{
        console.log(err);
      }
    })
  }



  

  updateBlog(){
    this.blogForm.imageId = this.image.id;
    const {title,noidung,imageId} = this.blogForm;
    console.log(this.blogForm);
    this.aboutService.updateAbout(title,noidung,imageId).subscribe({
      next: res =>{
        this.getList();
        this.showForm=false;
        this.showSuccess("Cập nhật thành công");
      },error: err =>{
        this.showError(err.message);
      }
    })
  }

  

  selectImage(event : any,res: any){
    let data = document.querySelectorAll('.list-image img');
    data.forEach(i =>{
      i.classList.remove('choosen');
    })
    event.target.classList.toggle("choosen");
    this.imageChoosen = res;
    this.disabled = false;
}

  chooseImage(){
    this.image = this.imageChoosen;
    this.showImage = false;
  }


  showSuccess(text: string) {
    this.messageService.add({severity:'success', summary: 'Success', detail: text});``
  }
  showError(text: string) {
    this.messageService.add({severity:'error', summary: 'Error', detail: text});
  }
  
  showWarn(text : string) {
    this.messageService.add({severity:'warn', summary: 'Warn', detail: text});
  }
}
