import {Component} from '@angular/core';
import {ProductService} from 'src/app/_service/product.service';
import {OrderService} from 'src/app/_service/order.service';
import {BlogService} from 'src/app/_service/blog.service';
import {faCalculator, faDollarSign, faComment, faClipboard, faFile} from '@fortawesome/free-solid-svg-icons';
import * as am4core from '@amcharts/amcharts4/core';
import * as am4charts from '@amcharts/amcharts4/charts';
import am4themesAnimated from '@amcharts/amcharts4/themes/animated';

am4core.useTheme(am4themesAnimated)

@Component({
  selector: 'app-charts',
  templateUrl: './charts.component.html',
  styleUrls: ['./charts.component.css']
})
export class ChartsComponent {
  totalProducts: number = 0;
  totalProductquantity: number = 0;
  totalRevenue: number = 0;
  totalOrder: number = 0;
  totalblog: number = 0;
  cacli = faCalculator;
  clip = faClipboard;
  dolo = faDollarSign;
  cmt = faComment;
  faf = faFile;
  data: any;
  options: any;
  dataChart1: any[] = [];
  dataChart2: any[] = [];

  constructor(private productService: ProductService, private orderService: OrderService, private blogService: BlogService) {
  }

  ngOnInit(): void {
    //gọi ra api tổng sản phẩm
    this.productService.getTotalProducts().subscribe(total => {
      this.totalProducts = total;
    });
    //gọi ra api tổng sản phẩm đã bán
    this.productService.getTotalProductQuantity().subscribe(total => {
      this.totalProductquantity = total;
    });
    // gọi tổng doanhthu
    this.orderService.getTotalRevenue().subscribe(revenue => {
      this.totalRevenue = revenue;
    });
    // gọi tổng đơn hàng
    this.orderService.getOders().subscribe(odrder => {
      this.totalOrder = odrder;
    });
    // gọi tổng blog
    this.blogService.getBlogtt().subscribe(blogss => {
      this.totalblog = blogss;
    });

    this.renderChart1();
    this.renderChart2();
  }

  renderChart1() {
    this.getDataChart1()

    const chart = am4core.create('chartdiv1', am4charts.XYChart)
    chart.data = this.dataChart1
    chart.language.locale['_decimalSeparator'] = ','
    chart.language.locale['_thousandSeparator'] = '.'
    chart.legend = new am4charts.Legend()
    chart.cursor = new am4charts.XYCursor()

    const categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis())
    categoryAxis.dataFields.category = 'field'
    categoryAxis.renderer.grid.template.disabled = true
    categoryAxis.renderer.minGridDistance = 10
    categoryAxis.renderer.cellStartLocation = 0.15
    categoryAxis.renderer.cellEndLocation = 0.85
    categoryAxis.renderer.labels.template.paddingLeft = 0
    categoryAxis.renderer.labels.template.paddingRight = 0

    const max = this.findMaxValue(this.dataChart1, 'value')
    const valueAxis1 = chart.yAxes.push(new am4charts.ValueAxis())
    valueAxis1.title.text = 'Đơn vị (VNĐ)'
    valueAxis1.min = 0
    valueAxis1.max = max + max * 0.1

    const series1 = chart.series.push(new am4charts.ColumnSeries())
    series1.dataFields.valueY = 'value'
    series1.dataFields.categoryX = 'field'
    series1.name = 'Doanh thu'
    series1.tooltipText = "Doanh thu: [bold]{valueY.formatNumber('#,###.##')}[/]"
    series1.columns.template.fill = am4core.color('#1cc88a').lighten(0.5)
    series1.strokeWidth = 0
    series1.columns.template.width = am4core.percent(75)
    series1.yAxis = valueAxis1

    const labelBullet1 = series1.bullets.push(new am4charts.LabelBullet())
    labelBullet1.label.verticalCenter = 'bottom'
    labelBullet1.label.dy = -5
    labelBullet1.label.text = "{valueY.formatNumber('#,###.#')}"
    labelBullet1.label.truncate = false
    labelBullet1.label.hideOversized = false
  }

  getDataChart1() {
    this.dataChart1 = [
      {field: 'Tháng 1', value: 1000000},
      {field: 'Tháng 2', value: 3000000},
      {field: 'Tháng 3', value: 5000000},
      {field: 'Tháng 4', value: 7000000},
      {field: 'Tháng 5', value: 2000000},
      {field: 'Tháng 6', value: 1000000},
      {field: 'Tháng 7', value: 9000000},
      {field: 'Tháng 8', value: 4000000},
    ]
  }

  renderChart2() {
    this.getDataChart2()

    const chart = am4core.create('chartdiv2', am4charts.XYChart)
    chart.data = this.dataChart2
    chart.language.locale['_decimalSeparator'] = ','
    chart.language.locale['_thousandSeparator'] = '.'
    chart.legend = new am4charts.Legend()
    chart.cursor = new am4charts.XYCursor()

    const categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis())
    categoryAxis.dataFields.category = 'field'
    categoryAxis.renderer.grid.template.disabled = true
    categoryAxis.renderer.minGridDistance = 10
    categoryAxis.renderer.cellStartLocation = 0.15
    categoryAxis.renderer.cellEndLocation = 0.85
    categoryAxis.renderer.labels.template.paddingLeft = 0
    categoryAxis.renderer.labels.template.paddingRight = 0

    const max = this.findMaxValue(this.dataChart2, 'value')
    const valueAxis1 = chart.yAxes.push(new am4charts.ValueAxis())
    valueAxis1.title.text = 'Đơn vị (cái)'
    valueAxis1.min = 0
    valueAxis1.max = max + max * 0.1

    const series1 = chart.series.push(new am4charts.ColumnSeries())
    series1.dataFields.valueY = 'value'
    series1.dataFields.categoryX = 'field'
    series1.name = 'Sản phẩm'
    series1.tooltipText = "Sản phẩm: [bold]{valueY.formatNumber('#,###.##')}[/]"
    series1.columns.template.fill = am4core.color('#4e73df').lighten(0.5)
    series1.strokeWidth = 0
    series1.columns.template.width = am4core.percent(75)
    series1.yAxis = valueAxis1

    const labelBullet1 = series1.bullets.push(new am4charts.LabelBullet())
    labelBullet1.label.verticalCenter = 'bottom'
    labelBullet1.label.dy = -5
    labelBullet1.label.text = "{valueY.formatNumber('#,###.#')}"
    labelBullet1.label.truncate = false
    labelBullet1.label.hideOversized = false
  }

  getDataChart2() {
    this.dataChart2 = [
      {field: 'Tháng 1', value: 10},
      {field: 'Tháng 2', value: 20},
      {field: 'Tháng 3', value: 15},
      {field: 'Tháng 4', value: 21},
      {field: 'Tháng 5', value: 22},
      {field: 'Tháng 6', value: 33},
      {field: 'Tháng 7', value: 56},
      {field: 'Tháng 8', value: 89},
    ]
  }

  findMaxValue (data: Array<object>, ...fields: string[]) {
    return data.reduce((max, item) => {
      // @ts-ignore
      const itemMax = Math.max(...fields.map((f) => item[f] || 0))
      return Math.max(max, itemMax)
    }, 0)
  }
}
