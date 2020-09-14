<template xmlns="http://www.w3.org/1999/html" xmlns:v-on="http://www.w3.org/1999/xhtml">
  <div class="app-container pull-auto">
    <basic-container>
      <div class="filter-container crud-menu">
        <div class="crud-menu_left">
          <input type="file" class="form-control" id="upload_file" name="upload_file" accept=".xlsx"
                 style="display: none;" @change='changeExcel'>
          <Input placeholder="请选择文件..." v-model="fileName" id="upload_file_tmp" style="width: 250px"
                 onclick="upload_file.click();" readonly/>
          <Button type="primary" icon="md-arrow-round-up" @click="selectExcel" class="controller_button">浏览</Button>
          <Button type="primary" icon="md-arrow-round-down" @click="saveExcel" class="controller_button">导出</Button>
          <Button type="primary" icon="ios-egg" @click="saveTemplate" class="controller_button">保存模板</Button>
        </div>
      </div>

      <div ref="excelView" id="excelView" :style="spreadStyle"></div>

    </basic-container>
  </div>

</template>
<style scoped>

</style>

<script>
  import axios from '@/router/axios'
  export default {
    data () {
      return {
        fileName: "",
        excelIo: {},
        spread: {},
        spreadStyle: {
          width: '100%',
          height: '430px'
        }
      };
    },
    mounted () {
      this.spread = new GC.Spread.Sheets.Workbook(document.getElementById("excelView"), {sheetCount: 1});
      this.excelIo = new GC.Spread.Excel.IO();
    },
    methods: {
      //上传EXCEL
      upExcel(){
        var excelFile = document.getElementById("upload_file").files[0];
        this.excelIo.open(excelFile, (json) => {
          var workbookObj = json;
          this.spread.fromJSON(workbookObj);
        }, function (e) {
          alert('文件读取失败，仅支持xlsx类型');
        });

      },
      //选择文件
      selectExcel(){
        upload_file.click();
      },
      changeExcel(){
        var excelFile = document.getElementById("upload_file").files[0];
        this.fileName = document.getElementById("upload_file").files[0].name;
        this.upExcel();
      },
      //保存EXCEL
      saveExcel(){
        //生成时间戳
        var fileName = new Date().getTime() + '.xlsx';
        var json = this.spread.toJSON();
        this.excelIo.save(json, (blob) => {
          saveAs(blob, fileName);
        }, function (e) {
          console.log(e);
        });
      },
      //保存模板
      saveTemplate(){
        //生成时间戳
        var fileName = new Date().getTime() + '.json';
        var json = this.spread.toJSON();
        var jsontext = JSON.stringify(json);
        //前台保存
        saveAs(new Blob([jsontext], {type: "text/plain;charset=utf-8"}), fileName);
        //保存后台
        let formData = new FormData();
        var thefile = document.getElementById("upload_file").files[0];
        formData.append("file", thefile);
        formData.append("fileName", thefile.name);
        axios.post('/admin/excelup/doImport', formData).then(function (response) {
          alert("模板保存成功");
        }).catch(function (error) {
          alert("上传失败");
          console.log(error);
        });

      }
    }
  }
</script>

