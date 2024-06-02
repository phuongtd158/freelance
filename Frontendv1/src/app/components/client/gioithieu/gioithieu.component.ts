import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogService } from 'src/app/_service/blog.service';
import { TagService } from 'src/app/_service/tag.service';
import { AboutService } from 'src/app/_service/about.service';

@Component({
  selector: 'app-gioithieu',
  templateUrl: './gioithieu.component.html',
  styleUrls: ['./gioithieu.component.css']
})
export class GioithieuComponent implements OnInit {

  listTag : any;
  listBlogNewest: any;
  blog: any;
  id: any;


  constructor(private router: Router,
    private aboutService:AboutService,
    private route: ActivatedRoute,
    private blogService: BlogService,
    private tagService: TagService){
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;

  }

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getBlog();
    this.getListTag();
    this.getListNewest();
  }


  getListTag(){
    this.tagService.getListTag().subscribe({
      next: res =>{
        this.listTag = res;
        
      },error: err=>{
        console.log(err);
      }
    })
  }

  getBlog(){
    this.aboutService.getListAbout().subscribe({
      next: res =>{
        this.blog = res[0];
        console.log(this.blog);
      },error: err=>{
        console.log(err);
      }
    })
  }

  getListNewest(){
    this.blogService.getListNewest(3).subscribe({
      next: res =>{
        this.listBlogNewest = res;
      },error: err=>{
        console.log(err);
      }
    })
  }


}
