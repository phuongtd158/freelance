export const ORDER_STATUS = Object.freeze({
  WAITING: 'WAITING',
  WAITING_DELIVERY: 'WAITING_DELIVERY',
  DELIVERED: 'DELIVERED',
  CANCELED: 'CANCELED',
  RETURNS: 'RETURNS',
  ERROR: 'ERROR',
  ON_DELIVERY: 'ON_DELIVERY',
  DONE: 'DONE',
  WAITING_CONFIRM_RETURNS: 'WAITING_CONFIRM_RETURNS',
  REJECT_RETURNS: 'REJECT_RETURNS'
})

export const REASON_OPTIONS: any = ['Chưa nhận được hàng', 'Thiếu hàng', 'Người bán gửi sai hàng', 'Hàng bể vỡ (vỡ vụn, trầy xước, không nguyên vẹn, rò rỉ chất lỏng...)', 'Hàng lỗi, không hoạt động', 'Khác với mô tả', 'Hàng đã qua sử dụng', 'Hàng giả/nhái', 'Thùng hàng rỗng / nghi ngờ lừa đảo']

export const ROLE = {
  ADMIN: 'ROLE_ADMIN',
  MODERATOR: 'ROLE_MODERATOR',
  EMPLOYEE: 'ROLE_EMPLOYEE',
  USER: 'ROLE_USER'
}
