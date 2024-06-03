import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";

const PREFIX_ADDRESS_API = 'https://online-gateway.ghn.vn/shiip/public-api/master-data'
const PREFIX_SHIPPING_API = 'https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order'


@Injectable({
  providedIn: 'root'
})
export class AboutService {
  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getProvince() {
    return this.http.get(`${PREFIX_ADDRESS_API}/province`);
  }

  getDistrict(provinceId: any) {
    return this.http.get(`PREFIX_ADDRESS_API/district?province_id=${provinceId}`);
  }

  getWard(districtId: any) {
    return this.http.get(`PREFIX_ADDRESS_API/ward?district_id=${districtId}`);
  }

  getService(data: any) {
    return this.http.post(`${PREFIX_SHIPPING_API}/available-services`, data);
  }

  getShippingOrder(data: any) {
    return this.http.post(`${PREFIX_SHIPPING_API}/fee`, data);
  }
}
