import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { SERVER_DOMAIN } from './domain.service';
import { map } from 'rxjs/operators';

const CONTACT_API = SERVER_DOMAIN + "/api/contact/";


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  constructor(private http: HttpClient) { }

    //lấy ra tất cả
  getList():Observable<any>{
    return this.http.get(CONTACT_API,httpOptions);
  }
    // láy theo id
  getContact(id: number):Observable<any>{
    return this.http.get(CONTACT_API + id,httpOptions);
  }
  // tạo mới
  createContact(name: string,gmail: string,content: string):Observable<any>{
    return  this.http.post(CONTACT_API +'create',{name,gmail,content},httpOptions);
  }
  // update
  updateContact(id: number,name: string,gmail: string,content: string):Observable<any>{
    return this.http.put(CONTACT_API + 'update/' +id,{id,name,gmail,content},httpOptions);
  }
  //xóa
  deleleContact(id: number){
    return this.http.delete(CONTACT_API + 'delete/' + id,httpOptions);
  }
  // enable trạng thái
  enableContact(id: number){
    return this.http.put(CONTACT_API + 'enable/'+ id,httpOptions);
  }

}
