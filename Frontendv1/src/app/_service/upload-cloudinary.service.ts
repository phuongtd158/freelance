import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {SERVER_DOMAIN} from "./domain.service";

@Injectable({
  providedIn: 'root'
})
export class UploadCloudinaryService {

  constructor(private http: HttpClient) {
  }

  upload(files: any) {
    return this.http.post(`${SERVER_DOMAIN}/api/image/upload`, files);
  }

  delete(publicId: any) {
    return this.http.delete(`${SERVER_DOMAIN}/api/image/${publicId}`);
  }
}
