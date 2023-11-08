<script src="http://widget.seniverse.com/widget/chameleon.js"></script>
<script>
export default {
  name: "Weather",
  created() {
    this.initWeatherWidget()
  },
  methods: {
    initWeatherWidget() {
      (function (T, h, i, n, k, P, a, g, e) {
        g = function () {
          P = h.createElement(i);
          a = h.getElementsByTagName(i)[0];
          P.src = k;
          P.charset = "utf-8";
          P.async = 1;
          a.parentNode.insertBefore(P, a);
        };
        T["ThinkPageWeatherWidgetObject"] = n;
        T[n] || (T[n] = function () {
          (T[n].q = T[n].q || []).push(arguments);
        });
        T[n].l = +new Date();
        if (T.attachEvent) {
          T.attachEvent("onload", g);
        } else {
          T.addEventListener("load", g, false);
        }
      }(window, document, "script", "tpwidget", "//widget.seniverse.com/widget/chameleon.js"));

      tpwidget("init", {
        "flavor": "slim",
        "location": "WK3N92NQV6RQ",
        "geolocation": "disabled",
        "language": "zh-chs",
        "unit": "c",
        "theme": "chameleon",
        "container": "tp-weather-widget",
        "bubble": "enabled",
        "alarmType": "badge",
        "uid": "UE8EB120D0",
        "hash": "c74526577e358dfb1ed5eef1e0218c31"
      });
      tpwidget("show");
    }

  }
}
</script>

<template>
  <div class="headerinfo">
    <span id="dateweek"></span>
    <div id="tp-weather-widget">
    </div>
  </div>
</template>

<style scoped>

</style>