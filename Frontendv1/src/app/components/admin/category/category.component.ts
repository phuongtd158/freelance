import {Component, OnInit} from '@angular/core';
import {MessageService} from 'primeng/api';
import {CategoryService} from 'src/app/_service/category.service';
import {UploadCloudinaryService} from "../../../_service/upload-cloudinary.service";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
  providers: [MessageService]

})

export class CategoryComponent implements OnInit {

  listCategory: any;

  displayForm: boolean = false;

  deleteForm: boolean = false;

  onUpdate: boolean = false;
  onCreate: boolean = false;
  showDropzone: boolean = true;

  categoryForm: any = {
    id: null,
    name: null,
    link: null
  }

  fileUploadImage: any[] = [];

  constructor(private messageService: MessageService, private categoryService: CategoryService, private uploadService: UploadCloudinaryService) {
  }

  ngOnInit(): void {
    this.getListCategory();
  }


  getListCategory() {
    this.categoryService.getListCategory().subscribe({
      next: res => {
        this.listCategory = res;
        console.log(res);
      }, error: err => {
        console.log(err);
      }
    })
  }

  showForm() {
    this.onUpdate = false;
    this.onCreate = true
    this.categoryForm = {
      id: null,
      name: null,
      link: null
    }
    this.showDropzone = true;
    this.displayForm = true;
  }


  onUpdateForm(id: number, name: string, link: string) {
    this.showDropzone = false
    this.onUpdate = true;
    this.onCreate = false;
    this.displayForm = true;
    this.categoryForm.id = id;
    this.categoryForm.name = name;
    this.categoryForm.link = link;
  }

  onDelete(id: number, name: string, link: string) {
    this.deleteForm = true;
    this.onCreate = false
    this.onUpdate = false
    this.categoryForm.id = id;
    this.categoryForm.name = name;
    this.categoryForm.link = link;

  }

  async uploadImg() {
    const formData = new FormData();
    for (let i = 0; i < this.fileUploadImage.length; i++) {
      formData.append('files', this.fileUploadImage[i]);
    }
    const rs: any = await this.uploadService.upload(formData).toPromise();
    return rs ? rs[0] : ''
  }

  async createCategory() {
    const {name} = this.categoryForm;
    if (!name) {
      this.showWarn(`Vui lòng nhập tên danh mục !`)
      return
    }
    if (!this.fileUploadImage.length && this.fileUploadImage.length <= 0) {
      this.showWarn(`Vui lòng chọn hình ảnh !`)
      return
    }
    this.categoryForm.link = await this.uploadImg()
    this.categoryService.createCategory(name, this.categoryForm.link).subscribe({
      next: res => {
        this.getListCategory();
        this.showSuccess("Tạo danh mục thành công!");
        this.onHide()
      }, error: err => {
        this.showError(err.message);
      }
    })
  }


  async updateCategory() {
    const {id, name} = this.categoryForm;
    if (!name) {
      this.showWarn(`Vui lòng nhập tên danh mục !`)
      return
    }
    if (this.showDropzone && !this.fileUploadImage.length && this.fileUploadImage.length <= 0) {
      this.showWarn(`Vui lòng chọn hình ảnh !`)
      return
    }
    if (this.showDropzone) {
      this.categoryForm.link = await this.uploadImg()
    }
    this.categoryService.updateCategory(id, name, this.categoryForm.link).subscribe({
      next: res => {
        this.getListCategory();
        this.showSuccess("Cập nhật danh mục thành công!");
        this.onHide()
      }, error: err => {
        this.showError(err.message);
      }
    })
  }


  enableCategory(id: number) {
    this.categoryService.enableCategory(id).subscribe({
      next: res => {
        this.getListCategory();
        this.showSuccess("Cập nhật thành công!!");
      }, error: err => {
        this.showError(err.message);
      }
    })
  }


  deleteCategory() {
    const {id} = this.categoryForm;
    this.categoryService.deleteCategory(id).subscribe({
      next: res => {
        this.getListCategory();
        this.showWarn("Xóa danh mục thành công!!");
        this.deleteForm = false;
      }, error: err => {
        this.showError(err.message);
      }
    })
  }

  onUploadImage($event: any) {
    this.fileUploadImage = $event.addedFiles;
  }

  onHide() {
    this.fileUploadImage = []
    this.categoryForm.link = null
    this.categoryForm.id = null
    this.categoryForm.name = null
    this.onCreate = false
    this.onUpdate = false
    this.showDropzone = false
    this.displayForm = false
  }

  onRemoveImage(f: any) {
    this.fileUploadImage.splice(this.fileUploadImage.indexOf(f), 1);
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
