
new Vue({
    el: '#app',
    data() {
        return {
            form: { account: '', password: '', checkPassword: '' },
            newBlogRules: {
                title: [
                    { required: true, message: '请输入博客标题', trigger: 'blur' },
                    { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
                ],
                content: [
                    { required: true, message: '请输入正文', trigger: 'blur' },
                    { min: 10, max: 20000, message: '正文至少100字', trigger: 'blur' }
                ],
                typeId: [
                    { required: true, message: '请选择类型', trigger: 'blur' }
                ]
            },
            personInfo: { name: '', avatar: '/statics/images/18908.png' },
            newBlog: { title: '', content: '', typeId: null },
            blogs: [],  // 博客列表
            pageParam: {  // 分页参数
                current: 1,
                size: 5,
                totalSize: 0
            },
            tags: [],  // 博客类型标签
            activeTab: 'followers',  // 当前活动标签（关注或粉丝）
            followers: [],  // 关注用户列表
            followed: [],  // 粉丝用户列表
            activeIndex: '0',  // 当前分类索引
            key: '',  // 搜索关键字
            dialogVisible: [false, false],  // 弹出框状态
            drawer: false  // 抽屉状态

        };

    },

    created() {
        this.initType();  // 初始化博客类型
        this.initUser();  // 获取用户信息
        this.initFollowers();  // 初始化关注列表
        this.initFolloweds();  // 初始化粉丝列表
        this.load();
        request.post('/followInfo', (res) => {
            this.follow.userId = res.userId;
        }); // 初次加载博客列表
    },
    methods: {
        // 分页事件处理
        handlePageChange(page) {
            this.pageParam.current = page;
            this.load();  // 加载新的页码的博客
        },
        onSubmitNew(form) {
            this.$refs[form].validate((valid) => {
                if (valid) {
                    // 添加 author 字段
                    this.newBlog.author = this.personInfo.userId;  // 如果 currentUser 为空，使用 '默认用户'

                    // 设置默认值
                    this.newBlog.readed = 0;

                    // 发送请求
                    request.post("/addBlog", this.newBlog, (res) => {
                        if (res) {
                            this.$message("发布成功");
                            this.toggleStatus(1);
                        }
                    });
                }
            });
        },

        // 加载博客数据
        load() {
            const data = {
                typeId: this.activeIndex === '0' ? undefined : this.activeIndex,
                key: this.key.trim() || undefined
            };
            console.log('Loading blogs with params:', this.pageParam, data);  // 打印分页参数
            this.initBlog(this.pageParam, data);  // 获取博客数据
        },

        // 从服务器获取博客数据
        async initBlog(params, data) {
            const url = '/blogs';
            await request.post(url, data, params, (res) => {
                if (res && res.list) {
                    this.blogs = res.list;
                    this.pageParam.totalSize = res.totalSize;  // 更新总条目数
                    console.log('Total Size:', res.totalNumber);  // 检查总条目数
                }
            });
        },

        // 初始化博客类型标签
        async initType() {
            await request.post("/types", (res) => { this.tags = res; });
        },

        // 获取用户信息
        async initUser() {
            await request.post("/personalInfomation", (res) => {
                this.personInfo = res;
            });
        },

        // 初始化关注列表
        async initFollowers() {
            await request.post("/followers", (res) => {
                this.followers = res;
            });
        },

        // 初始化粉丝列表
        async initFolloweds() {
            await request.post("/followeds", (res) => {
                this.followed = res;
            });
        },

        // 跳转到更多关注页面
        followerMore() {
            window.location = "/users/followers";
        },

        // 跳转到更多粉丝页面
        followedMore() {
            window.location = "/users/followed";
        },

        // 用户详情跳转
        userDetail(id) {
            console.log(id);
            window.location = "/personInfo/" + id;
        },

        // 跳转到用户个人信息页
        toPersonInfo() {
            if (this.personInfo.userId) {
                window.location = '/personInfo/' + this.personInfo.userId;
            } else {
                console.error('User ID not available!');
            }
        },
        toggleStatus(status) {
            console.log(status)
            console.log(!this.dialogVisible)
            this.$set(this.dialogVisible, status, !this.dialogVisible[status])
        },

        // 搜索功能处理
        search() {
            this.pageParam.current = 1;  // 重置页码为第一页
            this.blogs = [];  // 清空当前博客列表
            this.load();  // 重新加载博客数据
        },

        // 跳转到博客详情页
        blogDetail(id) {
            window.location = "/blog/" + id;
        }
    },

    filters: {
        // 日期格式化过滤器
        dateFormate(date) {
            return date.substring(0, 10);  // 格式化日期为 'YYYY-MM-DD'
        }
    }
});