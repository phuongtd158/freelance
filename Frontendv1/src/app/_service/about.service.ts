import { HttpHeaders,HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_DOMAIN } from './domain.service';
const ABOUT_API = SERVER_DOMAIN + "/api/about/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AboutService {


  constructor(private http: HttpClient) { }
  ngOnInit(): void {
          
  }

  
  updateAbout(title:string,noidung:string, imageId: number):Observable<any>{
    return this.http.put(ABOUT_API +'update/',{title,noidung,imageId},httpOptions);
  }

  getListAbout():Observable<any>{
    return this.http.get(ABOUT_API,httpOptions);
  }
  

}
