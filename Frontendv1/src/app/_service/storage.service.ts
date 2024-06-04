import { Injectable } from '@angular/core';

const USER_KEY = 'auth';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

//   clean():void{
//     window.sessionStorage.clear();
//   }


//   saveUser(user: any): void {
//     window.sessionStorage.removeItem(USER_KEY);
//     window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
//   }
//   getUser(): any {
//     const user = window.sessionStorage.getItem(USER_KEY);
//     if (user) {
//       return JSON.parse(user);
//     }

//     return {};
//   }
//   isLoggedIn(): boolean {
//     const user = window.sessionStorage.getItem(USER_KEY);
//     if (user) {
//       return true;
//     }

//     return false;
//   }
// }
clean():void{
  // Xóa cookie bằng cách đặt hết hạn cho cookie là một thời điểm trong quá khứ
  document.cookie = USER_KEY + '=;expires=Thu, 01 Jan 1970 00:00:00 GMT';
}

saveUser(user: any): void {
  // Tạo chuỗi JSON từ đối tượng người dùng
  const userJson = JSON.stringify(user);
  // Thiết lập cookie với key là USER_KEY và giá trị là chuỗi JSON
  document.cookie = USER_KEY + '=' + userJson;
}

getUser(): any {
  // Lấy tất cả các cookie hiện có
  const cookies = document.cookie.split(';');
  // Tìm cookie với key là USER_KEY
  for (let cookie of cookies) {
    const [key, value] = cookie.trim().split('=');
    if (key === USER_KEY) {
      // Trả về đối tượng người dùng từ cookie
      return JSON.parse(value);
    }
  }
  // Nếu không tìm thấy cookie, trả về một đối tượng rỗng
  return {};
}

isLoggedIn(): boolean {
  // Kiểm tra xem có cookie với key là USER_KEY hay không
  return !!this.getUser().id;
}
// isLoggedIn(): boolean {
//   const user = this.getUser();
//   if (user && user.exp) {
//     const expirationTime = user.exp * 1000; // Chuyển đổi từ giây thành mili-giây
//     const currentTime = new Date().getTime();
//     // Kiểm tra xem thời gian hết hạn của token có lớn hơn thời gian hiện tại hay không
//     return expirationTime > currentTime;
//   }
//   return false;
// }

}
