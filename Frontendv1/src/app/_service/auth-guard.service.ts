import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  // isLoggedIn: boolean = false;

  constructor(private storageService: StorageService,private router: Router) { }


// kiểm tra đăng nhập
//   canActivate():boolean{
//     this.isLoggedIn = this.storageService.isLoggedIn();
//     if(this.isLoggedIn == false){
//       this.router.navigate(['/login']);
//       return false;
//     }
//     return true;
//   }

// }
canActivate(): boolean {
  // Kiểm tra xem người dùng đã đăng nhập hay chưa bằng cách kiểm tra cookie
  const isLoggedIn = this.isLoggedIn();

  if (!isLoggedIn) {
    // Nếu người dùng chưa đăng nhập, chuyển hướng đến trang đăng nhập
    this.router.navigate(['/login']);
    return false;
  }

  // Nếu người dùng đã đăng nhập, cho phép truy cập vào các route được bảo vệ
  return true;
}

private isLoggedIn(): boolean {
  // Lấy tất cả các cookie
  const cookies = document.cookie.split(';');
  // Tìm cookie với key là 'auth'
  for (let cookie of cookies) {
    const [key, value] = cookie.trim().split('=');
    if (key === 'auth') {
      // Nếu tìm thấy cookie, kiểm tra xem giá trị của nó có rỗng hay không
      return value !== '';
    }
  }
  // Nếu không tìm thấy cookie 'auth', người dùng chưa đăng nhập
  return false;
}
}
