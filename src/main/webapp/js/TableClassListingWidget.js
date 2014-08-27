(function() {
  var TableClassListingWidget;

  TableClassListingWidget = (function() {

    function TableClassListingWidget() {}

    TableClassListingWidget.prototype.init = function(conf) {
      var _this = this;
      return LOM.getJSON("api/data/class/" + conf.classFullName + "/attributes", function(attributes) {
        return _this.drawTable(attributes, conf.classFullName);
      });
    };

    TableClassListingWidget.prototype.drawTable = function(attributesJson, classFullName) {
      var table,
        _this = this;
      this.page = LOM.emptyPage();
      table = $("<table>");
      this.page.append(table);
      this.buildTableHead(attributesJson, table);
      return LOM.getJSON("api/data/class/" + classFullName + "/instances", function(instances) {
        return _this.buildTableBody(instances, attributesJson, table, classFullName);
      });
    };

    TableClassListingWidget.prototype.buildTableHead = function(attributesJson, table) {
      var thead, trHead;
      thead = $("<thead>");
      table.append(thead);
      trHead = $("<tr>");
      trHead.attr("id", "attributes");
      thead.append(trHead);
      return attributesJson.forEach(function(attribute) {
        var thHead;
        thHead = $("<th>" + attribute.name + "</th>");
        thHead.attr("id", "attribute_" + attribute.id);
        return trHead.append(thHead);
      });
    };

    TableClassListingWidget.prototype.buildTableBody = function(instancesJson, attributesJson, table, classFullName) {
      var tbody,
        _this = this;
      if (instancesJson.length > 0) {
        tbody = $("<tbody>");
        tbody.attr("id", "instances");
        table.append(tbody);
        return instancesJson.forEach(function(instance) {
          return buildTableLine(instance, attributesJson, tbody, classFullName);
        });
      } else {
        return table.append("There are not instances");
      }
    };

    TableClassListingWidget.prototype.buildTableLine = function(instance, attributesJson, tbody, classFullName) {
      var trbody,
        _this = this;
      trbody = $("<tr>");
      trbody.attr("id", "instance_" + instance.id);
      tbody.append(trbody);
      attributesJson.forEach(function(attribute) {
        var td;
        td = $("<td>" + instance[attribute.name] + "</td>");
        td.attr("id", "instance_" + instance.id + "_attribute_" + attribute.id);
        return trbody.append(td);
      });
      return trbody.click(function() {
        return LOM.loadScript('api/widget/class/' + classFullName + '/edit', {
          classFullName: classFullName,
          id: instance.id
        });
      });
    };

    return TableClassListingWidget;

  })();

  return new TableClassListingWidget;

}).call(this);
