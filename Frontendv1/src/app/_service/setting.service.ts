import { HttpHeaders,HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_DOMAIN } from './domain.service';
const SETTING_API = SERVER_DOMAIN + "/api/setting/";
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class SettingService {


  constructor(private http: HttpClient) { }
  ngOnInit(): void {
          
  }

  
  updateSetting(gmail:string,phone:string,logo:string,facebook:string,youtube:string,telegram:string,instagram:string,diachi:string):Observable<any>{
    return this.http.put(SETTING_API +'update/',{gmail,phone,logo,facebook,youtube,telegram,instagram,diachi},httpOptions);
  }

  getListSetting():Observable<any>{
    return this.http.get(SETTING_API,httpOptions);
  }
  

}
