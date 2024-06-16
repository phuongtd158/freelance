import * as am4charts from '@amcharts/amcharts4/charts';
import * as am4core from '@amcharts/amcharts4/core';
import am4themesAnimated from '@amcharts/amcharts4/themes/animated';
import {DatePipe} from "@angular/common";
import {Component} from '@angular/core';
import {faCalculator, faClipboard, faComment, faDollarSign, faFile} from '@fortawesome/free-solid-svg-icons';
import {BlogService} from 'src/app/_service/blog.service';
import {ChartService} from 'src/app/_service/chart.service';
import {MessageCustomService} from "../../../_service/message-custom.service";

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
  visible1 = false;
  visible2 = false;
  dataDetail1 = [];
  dataDetail2 = [];

  constructor(private blogService: BlogService,
              private chartService: ChartService,
              private datePipe: DatePipe,
              private messageService: MessageCustomService) {
  }

  ngOnInit(): void {
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
      if (this.cycleType === 'DAY') {
        const timeDiff = this.rangeDate[1].getTime() - this.rangeDate[0].getTime();
        const diffDays = timeDiff / (1000 * 3600 * 24);
        if (diffDays > 29) {
          this.messageService.showWarn('Chỉ được chọn trong khoảng thời gian 30 ngày!')
          return
        }
      } else if (this.cycleType === 'MONTH') {
        const yearsDifference = this.rangeDate[1].getFullYear() - this.rangeDate[0].getFullYear();
        const monthsDifference = this.rangeDate[1].getMonth() - this.rangeDate[0].getMonth();
        const totalMonthsDifference = yearsDifference * 12 + monthsDifference;
        if (totalMonthsDifference > 6) {
          this.messageService.showWarn('Chỉ được chọn trong khoảng thời gian 7 tháng!')
          return;
        }
      } else {
        const yearsDifference = this.rangeDate[1].getFullYear() - this.rangeDate[0].getFullYear();
        if (yearsDifference > 6) {
          this.messageService.showWarn('Chỉ được chọn trong khoảng thời gian 7 năm!')
          return;
        }
      }

      this.onRenderAllChart()
    }
  }

  handleFormatDate(dateValue: Date) {
    return this.datePipe.transform(dateValue, 'yyyy-MM-dd')
  }

  onRenderAllChart() {
    this.chartService.getSumQuantityProduct(this.getParams()).subscribe(total => {
      this.totalProducts = total;
    });
    this.chartService.getSumRevenue(this.getParams()).subscribe(revenue => {
      this.totalRevenue = revenue;
    });
    this.chartService.getSumOrderDone(this.getParams()).subscribe(order => {
      this.totalOrder = order;
    });

    this.chartService.searchRevenue(this.getParams()).subscribe(res => {
      const chart = am4core.create('chartdiv1', am4charts.XYChart)
      chart.data = res
      chart.language.locale['_decimalSeparator'] = ','
      chart.language.locale['_thousandSeparator'] = '.'
      chart.cursor = new am4charts.XYCursor()

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
      series1.dataFields.valueY = 'value'
      series1.dataFields.categoryX = 'field'
      series1.tooltipText = "Doanh thu: [bold]{valueY.formatNumber('#,###.##')}[/]"
      series1.columns.template.fill = am4core.color('#1cc88a').lighten(0.5)
      series1.strokeWidth = 0
      series1.columns.template.width = am4core.percent(75)
      series1.yAxis = valueAxis1
    });

    this.chartService.searchProduct(this.getParams()).subscribe(res => {
      const chart = am4core.create('chartdiv2', am4charts.XYChart)
      chart.data = res
      chart.language.locale['_decimalSeparator'] = ','
      chart.language.locale['_thousandSeparator'] = '.'
      chart.cursor = new am4charts.XYCursor()

      const categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis())
      categoryAxis.dataFields.category = 'field'
      categoryAxis.renderer.grid.template.disabled = true
      categoryAxis.renderer.minGridDistance = 10
      categoryAxis.renderer.cellStartLocation = 0.15
      categoryAxis.renderer.cellEndLocation = 0.85
      categoryAxis.renderer.labels.template.paddingLeft = 0
      categoryAxis.renderer.labels.template.paddingRight = 0

      const valueAxis1 = chart.yAxes.push(new am4charts.ValueAxis())
      valueAxis1.title.text = 'Đơn vị (Cái)'
      valueAxis1.min = 0
      valueAxis1.maxPrecision = 0

      const series1 = chart.series.push(new am4charts.ColumnSeries())
      series1.dataFields.valueY = 'value'
      series1.dataFields.categoryX = 'field'
      series1.tooltipText = 'Số lượng: [bold]{valueY}[/]'
      series1.columns.template.fill = am4core.color('#4e73df').lighten(0.5)
      series1.strokeWidth = 0
      series1.columns.template.width = am4core.percent(75)
      series1.yAxis = valueAxis1
      series1.columns.template.events.on('hit', this.showConTextMenu1, this)
    });

    this.chartService.searchProductV2(this.getParams()).subscribe(res => {
      const chart = am4core.create('chartdiv3', am4charts.XYChart)
      chart.data = res
      chart.language.locale['_decimalSeparator'] = ','
      chart.language.locale['_thousandSeparator'] = '.'
      chart.cursor = new am4charts.XYCursor()

      const categoryAxis = chart.xAxes.push(new am4charts.CategoryAxis())
      categoryAxis.dataFields.category = 'field'
      categoryAxis.renderer.grid.template.disabled = true
      categoryAxis.renderer.minGridDistance = 10
      categoryAxis.renderer.cellStartLocation = 0.15
      categoryAxis.renderer.cellEndLocation = 0.85
      categoryAxis.renderer.labels.template.paddingLeft = 0
      categoryAxis.renderer.labels.template.paddingRight = 0
      categoryAxis.renderer.labels.template.wrap = true
      categoryAxis.renderer.labels.template.maxWidth = 150

      const valueAxis1 = chart.yAxes.push(new am4charts.ValueAxis())
      valueAxis1.title.text = 'Đơn vị (Cái)'
      valueAxis1.min = 0
      valueAxis1.maxPrecision = 0

      const series1 = chart.series.push(new am4charts.ColumnSeries())
      series1.dataFields.valueY = 'value'
      series1.dataFields.categoryX = 'field'
      series1.tooltipText = 'Số lượng: [bold]{valueY}[/]'
      series1.columns.template.fill = am4core.color('#36b9cc').lighten(0.5)
      series1.strokeWidth = 0
      series1.columns.template.width = am4core.percent(75)
      series1.yAxis = valueAxis1
      series1.columns.template.events.on('hit', this.showConTextMenu2, this)

      const chart_div = document.getElementById('chartdiv3')
      if (chart_div) {
        const numColsShow = 8
        let chartWidth =
          chart.data.length < numColsShow ? 100 : chart.data.length * (100 / numColsShow)
        chart_div.setAttribute('style', 'width: ' + chartWidth + '%')
      }
    })
  }

  export() {
    // @ts-ignore
    this.chartService.export(this.getParams()).subscribe(
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

  getParams() {
    return {
      cycleType: this.cycleType,
      fromDate: this.fromDate,
      toDate: this.toDate
    }
  }

  showConTextMenu1(ev: any) {
    const dateStr = ev.target.dataItem.dataContext.dateStr
    this.chartService.searchProductDetail({cycleType: this.cycleType, toDate: dateStr}).subscribe(res => {
      this.dataDetail1 = res
      this.visible1 = true
    })
  }

  showConTextMenu2(ev: any) {
    const productId = ev.target.dataItem.dataContext.productId
    this.chartService.searchProductV2Detail({...this.getParams(), productId: productId}).subscribe(res => {
      this.dataDetail2 = res
      this.visible2 = true
    })
  }
}
