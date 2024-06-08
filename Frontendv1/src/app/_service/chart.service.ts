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

  getSumQuantityProduct(): Observable<any> {
    return this.http.get(CHART_API + 'get-sum-quantity-product', httpOptions);
  }

  getSumRevenue(): Observable<any> {
    return this.http.get(CHART_API + 'get-sum-revenue', httpOptions);
  }

  getSumOrderDone(): Observable<any> {
    return this.http.get(CHART_API + 'get-sum-order-done', httpOptions);
  }

  getSumProductSell(): Observable<any> {
    return this.http.get(CHART_API + 'get-sum-product-sell', httpOptions);
  }

  export(data: any): Observable<any> {
    return this.http.post(CHART_API + 'export', data, {...httpOptions, responseType: 'blob'});
  }
}
