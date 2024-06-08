import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {SERVER_DOMAIN} from "./domain.service";

const SHIPPING_API = SERVER_DOMAIN + '/shipping'


@Injectable({
  providedIn: 'root'
})
export class ShippingService {
  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getProvince() {
    return this.http.get(`${SHIPPING_API}/province`);
  }

  getDistrict(provinceId: any) {
    return this.http.get(`${SHIPPING_API}/district?provinceId=${provinceId}`);
  }

  getWard(districtId: any) {
    return this.http.get(`${SHIPPING_API}/ward?districtId=${districtId}`);
  }

  getService(data: any) {
    return this.http.post(`${SHIPPING_API}/available-services`, data);
  }

  getShippingOrder(data: any) {
    return this.http.post(`${SHIPPING_API}/fee`, data);
  }
}
