/*
实例化camvas配置参数
config = {
                video:{width:Number(scale*4),height:Number(scale*3)},//视频比例4:3
                canvasId:'canvas',//画布canvas节点ID
                videoId:'v',//video节点ID
                imgType:'png',//图片类型,/png|jpeg|bmp|gif/
                quality:'1' //图片质量0-1之间
            }
*/

window.URL = window.URL || window.webkitURL||window.mozURL || window.msURL;

navigator.getUserMedia  = navigator.getUserMedia ||
    navigator.webkitGetUserMedia ||
    navigator.mozGetUserMedia ||
    navigator.msGetUserMedia

window.requestAnimationFrame = window.requestAnimationFrame ||
    window.webkitRequestAnimationFrame ||
    window.mozRequestAnimationFrame ||
    window.msRequestAnimationFrame ||
    window.oRequestAnimationFrame

// Integrate navigator.getUserMedia & navigator.mediaDevices.getUserMedia
function getUserMedia (constraints, successCallback, errorCallback) {
    if (!constraints || !successCallback || !errorCallback) {return}

    if (navigator.mediaDevices) {
        navigator.mediaDevices.getUserMedia(constraints).then(successCallback, errorCallback)
    } else {
        navigator.getUserMedia(constraints, successCallback, errorCallback)
    }
}

//获取摄像头设备源
function getMediaStream() {
    var exArray = []; //存储设备源ID
    MediaStreamTrack.getSources(function (sourceInfos) {
        for (var i = 0; i != sourceInfos.length; ++i) {
            var sourceInfo = sourceInfos[i];
            //这里会遍历audio,video，所以要加以区分
            if (sourceInfo.kind === 'video') {
                exArray.push(sourceInfo.id);
            }
        }
    });
    return exArray;
}

//用户手机端使用后置摄像头
function getMediaConfig() {
    if (navigator.getUserMedia) {
        if(/Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent)){
            //手机端
            return {
                'video':
                    {'optional': [
                            {'sourceId': getMediaStream()[1] //0为前置摄像头，1为后置
                            }]
                    },
                'audio':false
            }
        }else{
            // 下面注释为强制使用后置摄像头
            /*return { 'audio': false, 'video': { 'facingMode': { 'exact': "environment" } } }*/
            return {'video':true,'audio':false}
        }
    }
    else {
        alert('Native device media streaming (getUserMedia) not supported in this browser.');
    }
}

// The function takes a canvas context and a `drawFunc` function.
// `drawFunc` receives two parameters, the video and the time since
// the last time it was called.
function camvas(config) {
    var self = this
    self.convas = document.getElementById(config.canvasId)
    self.ctx = self.convas.getContext('2d');
    self.config = config
    self.isStop = false;

    //video节点ID
    self.video = document.getElementById(self.config.videoId)

    //video 显示尺寸
    self.video.setAttribute('width', this.config.video.width)
    self.video.setAttribute('height', this.config.video.height)

    //视频流控制句柄
    var mediaStreamTrack;
    //对外开启视频方法
    this.startCamera = function () {
        // The callback happens when we are starting to stream the video.
        getUserMedia(getMediaConfig(), function(stream) {
            // Yay, now our webcam input is treated as a normal video and
            // we can start having fun
            try {
                mediaStreamTrack = typeof stream.stop === 'function' ? stream : stream.getTracks().length==1 ?
                    stream.getTracks()[0]:stream.getTracks()[1];
                if(self.video.mozSrcObject !== undefined){
                    //Firefox中，video.mozSrcObject最初为null，而不是未定义的，我们可以靠这个来检测Firefox的支持
                    self.video.mozSrcObject = stream;
                }else{
                    self.video.srcObject = stream;
                }
            } catch (error) {
                self.video.src = window.URL && window.URL.createObjectURL(stream) || stream;
            }
            self.isStop = false;
            self.video.play();
            // Let's start drawing the canvas!
            // self.recordVideo()
        }, function(err){
            alert(err);
        })
    }

    //录像方法
    this.recordVideo = function() {
        var self = this
        var last = Date.now()
        var loop = function() {
            // For some effects, you might want to know how much time is passed
            // since the last frame; that's why we pass along a Delta time `dt`
            // variable (expressed in milliseconds)
            var dt = Date.now() - last
            self.draw(self.video, dt)
            last = Date.now()
            requestAnimationFrame(loop)
        }
        requestAnimationFrame(loop)
    }
    //停止视频
    this.stop = function () {
        self.ctx.clearRect(0, 0, self.config.video.width,self.config.video.height);
        mediaStreamTrack && mediaStreamTrack.stop();
        self.isStop = true;
    }
    //拍照，base64/image/png
    this.drawImage=function (callback) {
        if(!self.isStop){
            self.ctx.drawImage(self.video,0,0,self.config.video.width,self.config.video.height);
            var base64URL = self.convas.toDataURL('image/'+self.config.imgType,self.config.quality);
            callback&&callback(base64URL);
        }
    }

    //录像数据帧
    this.draw = function(video, dt) {
        self.ctx.drawImage(video, 0, 0)
    }
}
