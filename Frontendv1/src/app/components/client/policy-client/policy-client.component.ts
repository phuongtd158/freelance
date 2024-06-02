import { Component, OnInit } from '@angular/core';
import { BlogService } from 'src/app/_service/blog.service';
import { TagService } from 'src/app/_service/tag.service';
import { PolicyService } from 'src/app/_service/policy.service';
@Component({
  selector: 'app-policy-client',
  templateUrl: './policy-client.component.html',
  styleUrls: ['./policy-client.component.css']
})
export class PolicyClientComponent implements OnInit {


  listTag : any;
  listBlog : any;
  listBlogNewest: any;

  constructor(private tagService: TagService,
    private policyService:PolicyService,private blogService: BlogService){

  }

  ngOnInit(): void {
    this.getListBlog();
    this.getListTag();
    this.getListNewest();
  }

  getListTag(){
    this.tagService.getListTag().subscribe({
      next: res =>{
        this.listTag = res;
      },error: err =>{
        console.log(err);
      }
    })
  }

  getListBlog(){
    this.policyService.getList().subscribe({
      next: res =>{
        this.listBlog = res;
        console.log(this.listBlog)
      },error: err =>{
        console.log(err);
      }
    })
  }
  scrollToTop() {
    const scrollDuration = 1000; // Thời gian để cuộn lên (1 giây)
    const scrollStep = -window.scrollY / (scrollDuration / 15);
  
    const scrollInterval = setInterval(function() {
        if (window.scrollY != 0) {
            window.scrollBy(0, scrollStep);
        } else {
            clearInterval(scrollInterval);
        }
    }, 15);
  }
  getListNewest(){
    this.blogService.getListNewest(3).subscribe({
      next: res=>{
        this.listBlogNewest = res;
      },error: err =>{
        console.log(err);
      }
    })
  }
  truncateHTMLContent(content: string): string {
    const maxLength = 500; // Giới hạn số ký tự
    if (content.length > maxLength) {
      return content.substring(0, maxLength) + '...'; // Cắt chuỗi nếu nó dài hơn maxLength
    }
    return content; // Trả về nội dung gốc nếu nó không vượt quá maxLength
  }
  


}
