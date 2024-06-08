import {Component} from '@angular/core';
import {ProductService} from 'src/app/_service/product.service';
import {OrderService} from 'src/app/_service/order.service';
import {BlogService} from 'src/app/_service/blog.service';
import {ChartService} from 'src/app/_service/chart.service';
import {faCalculator, faDollarSign, faComment, faClipboard, faFile} from '@fortawesome/free-solid-svg-icons';
import * as am4core from '@amcharts/amcharts4/core';
import * as am4charts from '@amcharts/amcharts4/charts';
import am4themesAnimated from '@amcharts/amcharts4/themes/animated';
import {DatePipe} from "@angular/common";

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

  // chart
  cycleType: string = 'DAY';
  listCycleType: any[] = [
    {name: 'Ngày', value: 'DAY'},
    {name: 'Tháng', value: 'MONTH'},
    {name: 'Năm', value: 'YEAR'},
  ]
  rangeDate: Date[] = [];
  fromDate: string | null = '';
  toDate: string | null = '';

  constructor(private blogService: BlogService,
              private chartService: ChartService,
              private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    //gọi ra api tổng sản phẩm
    this.chartService.getSumQuantityProduct().subscribe(total => {
      this.totalProducts = total;
    });
    //gọi ra api tổng sản phẩm đã bán
    this.chartService.getSumProductSell().subscribe(total => {
      this.totalProductquantity = total;
    });
    // gọi tổng doanhthu
    this.chartService.getSumRevenue().subscribe(revenue => {
      this.totalRevenue = revenue;
    });
    // gọi tổng đơn hàng
    this.chartService.getSumOrderDone().subscribe(odrder => {
      this.totalOrder = odrder;
    });
    // gọi tổng blog
    this.blogService.getBlogtt().subscribe(blogss => {
      this.totalblog = blogss;
    });

    // set value default rangeDate
    const today = new Date();
    const sevenDaysAgo = new Date(today);
    sevenDaysAgo.setDate(today.getDate() - 7);
    this.rangeDate = [sevenDaysAgo, today];
    this.fromDate = this.handleFormatDate(sevenDaysAgo);
    this.toDate = this.handleFormatDate(today);

    // render chart
    this.onRenderAllChart()
  }

  changeCycleType() {
    if (this.cycleType === 'DAY') {
      const today = new Date();
      const sevenDaysAgo = new Date(today);
      sevenDaysAgo.setDate(today.getDate() - 7);

      this.rangeDate = [sevenDaysAgo, today];
    } else if (this.cycleType === 'MONTH') {
      const monthCurr = new Date()
      monthCurr.setDate(1)

      const sixMonthsAgo = new Date(monthCurr);
      sixMonthsAgo.setMonth(monthCurr.getMonth() - 6);

      this.rangeDate = [sixMonthsAgo, monthCurr];
    } else if (this.cycleType === 'YEAR') {
      const yearCurr = new Date()
      yearCurr.setMonth(0)
      yearCurr.setDate(1)

      const threeYearsAgo = new Date(yearCurr);
      threeYearsAgo.setFullYear(yearCurr.getFullYear() - 3);

      this.rangeDate = [threeYearsAgo, yearCurr];
    }

    this.fromDate = this.handleFormatDate(this.rangeDate[0]);
    this.toDate = this.handleFormatDate(this.rangeDate[1]);

    this.onRenderAllChart()
  }

  changeRangeDate() {
    this.fromDate = this.handleFormatDate(this.rangeDate[0]);
    this.toDate = this.handleFormatDate(this.rangeDate[1]);

    if (this.fromDate && this.toDate) {
      this.onRenderAllChart()
    }
  }

  handleFormatDate(dateValue: Date) {
    return this.datePipe.transform(dateValue, 'yyyy-MM-dd')
  }

  onRenderAllChart() {
    this.renderChart1();
  }

  renderChart1() {
    const data = {
      cycleType: this.cycleType,
      fromDate: this.fromDate,
      toDate: this.toDate
    }
    this.chartService.searchRevenue(data).subscribe(res => {
      const chart = am4core.create('chartdiv1', am4charts.XYChart)
      chart.data = res
      chart.language.locale['_decimalSeparator'] = ','
      chart.language.locale['_thousandSeparator'] = '.'
      chart.cursor = new am4charts.XYCursor()
      chart.legend = new am4charts.Legend()

      const categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis())
      categoryAxis.dataFields.category = 'field'
      categoryAxis.renderer.grid.template.disabled = true
      categoryAxis.renderer.minGridDistance = 10
      categoryAxis.renderer.cellStartLocation = 0.15
      categoryAxis.renderer.cellEndLocation = 0.85
      categoryAxis.renderer.labels.template.paddingLeft = 0
      categoryAxis.renderer.labels.template.paddingRight = 0

      const valueAxis1 = chart.yAxes.push(new am4charts.ValueAxis())
      valueAxis1.title.text = 'Đơn vị (VNĐ)'
      valueAxis1.min = 0
      valueAxis1.maxPrecision = 0

      const series1 = chart.series.push(new am4charts.ColumnSeries())
      series1.dataFields.valueY = 'amount'
      series1.dataFields.categoryX = 'field'
      series1.name = 'Số lượng'
      series1.columns.template.tooltipText = 'Số lượng: [bold]{valueY}[/]'
      series1.columns.template.fill = am4core.color('#4285f4').lighten(0.5)
      series1.strokeWidth = 0
      series1.columns.template.width = am4core.percent(75)
      series1.yAxis = valueAxis1

      const valueAxis2 = chart.yAxes.push(new am4charts.ValueAxis())
      valueAxis2.title.text = 'Đơn vị (Cái)'
      valueAxis2.min = 0
      valueAxis2.maxPrecision = 0
      valueAxis2.renderer.opposite = true
      // @ts-ignore
      valueAxis2.syncWithAxis = chart.yAxes.getIndex(0)

      const series2 = chart.series.push(new am4charts.ColumnSeries())
      series2.dataFields.valueY = 'value'
      series2.dataFields.categoryX = 'field'
      series2.name = 'Doanh thu'
      series2.tooltipText = "Doanh thu: [bold]{valueY.formatNumber('#,###.##')}[/]"
      series2.columns.template.fill = am4core.color('#1cc88a').lighten(0.5)
      series2.strokeWidth = 0
      series2.columns.template.width = am4core.percent(75)
      series2.yAxis = valueAxis2
    });
  }

  export() {
    const data = {
      cycleType: this.cycleType,
      fromDate: this.fromDate,
      toDate: this.toDate
    }

    // @ts-ignore
    this.chartService.export(data).subscribe(
      (response: Blob) => {
        const url = window.URL.createObjectURL(new Blob([response]));
        const link = document.createElement('a');
        link.href = url;
        link.download = 'Statistical.xlsx';
        link.click();
        window.URL.revokeObjectURL(url);
      },
      error => {
        console.error('Error Export Excel:', error);
      }
    );
  }
}
