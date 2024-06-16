import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_DOMAIN} from './domain.service';

const CHART_API = SERVER_DOMAIN + "/api/chart/";
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class ChartService {
  products: any[] = [];

  constructor(private http: HttpClient) {
  }

  searchRevenue(data: any): Observable<any> {
    return this.http.post(CHART_API + 'search-revenue', data, httpOptions);
  }

  searchProduct(data: any): Observable<any> {
    return this.http.post(CHART_API + 'search-product', data, httpOptions);
  }

  searchProductDetail(data: any): Observable<any> {
    return this.http.post(CHART_API + 'search-product-detail', data, httpOptions);
  }

  searchProductV2(data: any): Observable<any> {
    return this.http.post(CHART_API + 'search-product-v2', data, httpOptions);
  }

  searchProductV2Detail(data: any): Observable<any> {
    return this.http.post(CHART_API + 'search-product-v2-detail', data, httpOptions);
  }

  getSumQuantityProduct(data: any): Observable<any> {
    return this.http.post(CHART_API + 'get-sum-quantity-product', data, httpOptions);
  }

  getSumRevenue(data: any): Observable<any> {
    return this.http.post(CHART_API + 'get-sum-revenue', data, httpOptions);
  }

  getSumOrderDone(data: any): Observable<any> {
    return this.http.post(CHART_API + 'get-sum-order-done', data, httpOptions);
  }

  export(data: any): Observable<any> {
    return this.http.post(CHART_API + 'export', data, {...httpOptions, responseType: 'blob'});
  }
}
