import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BlogService } from 'src/app/_service/blog.service';
import { TagService } from 'src/app/_service/tag.service';
import { PolicyService } from 'src/app/_service/policy.service';

@Component({
  selector: 'app-policy-detail',
  templateUrl: './policy-detail.component.html',
  styleUrls: ['./policy-detail.component.css']
})
export class PolicyDetailComponent implements OnInit {

  listTag : any;
  listBlogNewest: any;
  blog: any;
  id: any;


  constructor(private router: Router,
    private route: ActivatedRoute,
    private policyService:PolicyService,
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
    this.policyService.getPolicy(this.id).subscribe({
      next: res =>{
        this.blog = res;
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
