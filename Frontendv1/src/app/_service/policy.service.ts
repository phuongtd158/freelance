import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_DOMAIN } from './domain.service';
import { map } from 'rxjs/operators';


const POLICY_API = SERVER_DOMAIN + "/api/policy/";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class PolicyService {

  constructor(private http: HttpClient) { }
    //lấy ra tất cả
    getList():Observable<any>{
        return this.http.get(POLICY_API,httpOptions);
    }
    // láy theo id
    getPolicy(id: number):Observable<any>{
        return this.http.get(POLICY_API + id,httpOptions);
    }
    // lấy theo kiểu
    getListPolicyBykieu(id: number):Observable<any>{
        return this.http.get(POLICY_API + 'kieu/' + id,httpOptions);
    }
    //thêm
    createPolicy(title: string,content: string,kieu:number, imageId: number):Observable<any>{
        return  this.http.post(POLICY_API +'create',{title,content,kieu,imageId},httpOptions);
    }
    // sửa
    updatePolicy(id: number,title: string,content: string,kieu:number, imageId: number):Observable<any>{
        return this.http.put(POLICY_API + 'update/' +id,{id,title,content,kieu,imageId},httpOptions);
    }
    //xóa
    delelePolicy(id: number){
        return this.http.delete(POLICY_API + 'delete/' + id,httpOptions);
    }
    // kích hoaat
    enablePolicy(id: number){
        return this.http.put(POLICY_API + 'enable/'+ id,httpOptions);
      }

}
