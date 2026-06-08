-- ================================================
-- SpecFlow Database Initialization Script
-- ================================================

CREATE DATABASE IF NOT EXISTS specflow DEFAULT CHARACTER SET utf8mb4;
USE specflow;

-- ================================================
-- 1. sf_user — 用户表
-- ================================================
CREATE TABLE sf_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    password_hash VARCHAR(255) NOT NULL COMMENT '密码哈希',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    avatar_url VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    status VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '状态:active/inactive/locked',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除:0未删除,1已删除',
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ================================================
-- 2. sf_project — 项目表
-- ================================================
CREATE TABLE sf_project (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    owner_id BIGINT NOT NULL COMMENT '所有者用户ID',
    name VARCHAR(100) NOT NULL COMMENT '项目名称',
    description TEXT COMMENT '项目描述',
    requirement_raw TEXT COMMENT '原始需求',
    tech_stack VARCHAR(500) DEFAULT NULL COMMENT '技术栈JSON',
    api_token VARCHAR(64) DEFAULT NULL COMMENT 'API Token',
    status VARCHAR(20) NOT NULL DEFAULT 'active' COMMENT '状态:active/archived/archived',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    KEY idx_owner_id (owner_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目表';

-- ================================================
-- 3. sf_project_document — 开发文档表
-- ================================================
CREATE TABLE sf_project_document (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    doc_type VARCHAR(50) NOT NULL COMMENT '文档类型:requirement/design/api/test/other',
    title VARCHAR(200) NOT NULL COMMENT '文档标题',
    content MEDIUMTEXT COMMENT '文档内容',
    version INT NOT NULL DEFAULT 1 COMMENT '版本号',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_project_doc_type (project_id, doc_type),
    KEY idx_project_id (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目文档表';

-- ================================================
-- 4. sf_phase — 阶段表
-- ================================================
CREATE TABLE sf_phase (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    name VARCHAR(100) NOT NULL COMMENT '阶段名称',
    description TEXT COMMENT '阶段描述',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序顺序',
    status VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '状态:pending/in_progress/completed/blocked',
    is_gated TINYINT NOT NULL DEFAULT 0 COMMENT '是否有门控检查:0否,1是',
    gated_at DATETIME DEFAULT NULL COMMENT '门控通过时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_project_id (project_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='阶段表';

-- ================================================
-- 5. sf_task — 任务表
-- ================================================
CREATE TABLE sf_task (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    phase_id BIGINT NOT NULL COMMENT '所属阶段ID',
    title VARCHAR(200) NOT NULL COMMENT '任务标题',
    description TEXT COMMENT '任务描述',
    status VARCHAR(20) NOT NULL DEFAULT 'todo' COMMENT '状态:todo/in_progress/review/done/blocked',
    priority VARCHAR(20) NOT NULL DEFAULT 'medium' COMMENT '优先级:low/medium/high/critical',
    sort_order INT NOT NULL DEFAULT 0 COMMENT '排序顺序',
    acceptance_criteria JSON DEFAULT NULL COMMENT '验收标准JSON',
    estimated_files JSON DEFAULT NULL COMMENT '预估涉及文件JSON',
    created_by BIGINT NOT NULL COMMENT '创建者用户ID',
    started_at DATETIME DEFAULT NULL COMMENT '开始时间',
    completed_at DATETIME DEFAULT NULL COMMENT '完成时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    KEY idx_phase_id (phase_id),
    KEY idx_status (status),
    KEY idx_priority (priority),
    KEY idx_created_by (created_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务表';

-- ================================================
-- 6. sf_task_file_relation — 任务文件关联表
-- ================================================
CREATE TABLE sf_task_file_relation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    task_id BIGINT NOT NULL COMMENT '任务ID',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    relation_type VARCHAR(50) NOT NULL DEFAULT 'implement' COMMENT '关联类型:implement/modify/reference/test',
    KEY idx_task_id (task_id),
    UNIQUE KEY uk_task_file (task_id, file_path)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='任务文件关联表';

-- ================================================
-- 7. sf_phase_gate_log — Phase 门控日志表
-- ================================================
CREATE TABLE sf_phase_gate_log (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    phase_id BIGINT NOT NULL COMMENT '阶段ID',
    action VARCHAR(50) NOT NULL COMMENT '操作:check/pass/force_pass/reject',
    check_result JSON DEFAULT NULL COMMENT '检查结果JSON',
    error_count INT NOT NULL DEFAULT 0 COMMENT '错误数',
    warn_count INT NOT NULL DEFAULT 0 COMMENT '警告数',
    api_test_pass TINYINT DEFAULT NULL COMMENT 'API测试是否通过:0否,1是',
    all_tasks_done TINYINT DEFAULT NULL COMMENT '所有任务是否完成:0否,1是',
    passed TINYINT DEFAULT NULL COMMENT '门控是否通过:0否,1是',
    operated_by BIGINT DEFAULT NULL COMMENT '操作人用户ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    KEY idx_project_id (project_id),
    KEY idx_phase_id (phase_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Phase门控日志表';

-- ================================================
-- 8. sf_api_test_result — API 测试结果表
-- ================================================
CREATE TABLE sf_api_test_result (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    check_run_id VARCHAR(36) DEFAULT NULL COMMENT '检查批次ID(UUID)',
    endpoint VARCHAR(500) NOT NULL COMMENT 'API端点',
    expected_status INT DEFAULT NULL COMMENT '期望状态码',
    actual_status INT DEFAULT NULL COMMENT '实际状态码',
    response_body TEXT COMMENT '响应体',
    passed TINYINT DEFAULT NULL COMMENT '是否通过:0否,1是',
    error_message TEXT COMMENT '错误信息',
    checked_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '检查时间',
    KEY idx_project_id (project_id),
    KEY idx_check_run_id (check_run_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='API测试结果表';

-- ================================================
-- 9. sf_constraint_rule — 约束规则表
-- ================================================
CREATE TABLE sf_constraint_rule (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT DEFAULT NULL COMMENT '项目ID,NULL表示全局规则',
    name VARCHAR(100) NOT NULL COMMENT '规则名称',
    category VARCHAR(50) NOT NULL COMMENT '规则类别:security/performance/style/best_practice',
    severity VARCHAR(20) NOT NULL DEFAULT 'warning' COMMENT '严重级别:info/warning/error/critical',
    rule_definition JSON NOT NULL COMMENT '规则定义JSON',
    fix_suggestion TEXT COMMENT '修复建议',
    is_enabled TINYINT NOT NULL DEFAULT 1 COMMENT '是否启用:0否,1是',
    is_builtin TINYINT NOT NULL DEFAULT 0 COMMENT '是否内置:0否,1是',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_project_id (project_id),
    KEY idx_category (category),
    KEY idx_is_enabled (is_enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='约束规则表';

-- ================================================
-- 10. sf_constraint_violation — 约束违规表
-- ================================================
CREATE TABLE sf_constraint_violation (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    rule_id BIGINT NOT NULL COMMENT '规则ID',
    task_id BIGINT DEFAULT NULL COMMENT '关联任务ID',
    file_path VARCHAR(500) DEFAULT NULL COMMENT '违规文件路径',
    line_number INT DEFAULT NULL COMMENT '违规行号',
    message TEXT NOT NULL COMMENT '违规信息',
    fix_suggestion TEXT COMMENT '修复建议',
    severity VARCHAR(20) NOT NULL COMMENT '严重级别',
    status VARCHAR(20) NOT NULL DEFAULT 'open' COMMENT '状态:open/fixed/ignored/whitelisted',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发现时间',
    KEY idx_project_id (project_id),
    KEY idx_rule_id (rule_id),
    KEY idx_task_id (task_id),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='约束违规表';

-- ================================================
-- 11. sf_project_file — 项目文件表
-- ================================================
CREATE TABLE sf_project_file (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_type VARCHAR(50) DEFAULT NULL COMMENT '文件类型',
    content_md5 VARCHAR(32) DEFAULT NULL COMMENT '内容MD5',
    last_scanned DATETIME DEFAULT NULL COMMENT '最后扫描时间',
    UNIQUE KEY uk_project_file (project_id, file_path),
    KEY idx_file_type (file_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='项目文件表';

-- ================================================
-- 12. sf_ai_chat_session — AI 对话会话表
-- ================================================
CREATE TABLE sf_ai_chat_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    task_id BIGINT DEFAULT NULL COMMENT '关联任务ID',
    title VARCHAR(200) NOT NULL COMMENT '会话标题',
    created_by BIGINT NOT NULL COMMENT '创建者用户ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_project_id (project_id),
    KEY idx_task_id (task_id),
    KEY idx_created_by (created_by)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI对话会话表';

-- ================================================
-- 13. sf_ai_chat_message — AI 对话消息表
-- ================================================
CREATE TABLE sf_ai_chat_message (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    session_id BIGINT NOT NULL COMMENT '会话ID',
    sender_type VARCHAR(20) NOT NULL COMMENT '发送者类型:user/assistant/system',
    content TEXT NOT NULL COMMENT '消息内容',
    injected_context JSON DEFAULT NULL COMMENT '注入的上下文JSON',
    model_provider VARCHAR(50) DEFAULT NULL COMMENT '模型提供商',
    model_name VARCHAR(100) DEFAULT NULL COMMENT '模型名称',
    tokens_used INT DEFAULT NULL COMMENT '消耗Token数',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    KEY idx_session_id (session_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AI对话消息表';

-- ================================================
-- 14. sf_github_connection — GitHub 连接表
-- ================================================
CREATE TABLE sf_github_connection (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    github_user_id BIGINT NOT NULL COMMENT 'GitHub用户ID',
    github_username VARCHAR(100) NOT NULL COMMENT 'GitHub用户名',
    access_token VARCHAR(255) NOT NULL COMMENT 'Access Token(加密存储)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_user_id (user_id),
    KEY idx_github_user_id (github_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='GitHub连接表';

-- ================================================
-- 15. sf_github_repo — GitHub 仓库关联表
-- ================================================
CREATE TABLE sf_github_repo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    connection_id BIGINT NOT NULL COMMENT 'GitHub连接ID',
    repo_full_name VARCHAR(200) NOT NULL COMMENT '仓库全名:owner/repo',
    repo_url VARCHAR(500) NOT NULL COMMENT '仓库URL',
    webhook_secret VARCHAR(100) DEFAULT NULL COMMENT 'Webhook密钥',
    webhook_id VARCHAR(50) DEFAULT NULL COMMENT 'Webhook ID',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_project_id (project_id),
    KEY idx_connection_id (connection_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='GitHub仓库关联表';

-- ================================================
-- 16. sf_github_commit — GitHub Commit 记录表
-- ================================================
CREATE TABLE sf_github_commit (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    repo_id BIGINT NOT NULL COMMENT '仓库ID(sf_github_repo.id)',
    sha VARCHAR(40) NOT NULL COMMENT 'Commit SHA',
    message TEXT NOT NULL COMMENT 'Commit信息',
    author_name VARCHAR(100) DEFAULT NULL COMMENT '提交者名称',
    matched_task_id BIGINT DEFAULT NULL COMMENT '匹配的任务ID',
    match_method VARCHAR(50) DEFAULT NULL COMMENT '匹配方式:keyword/ai/commit_msg',
    pushed_at DATETIME DEFAULT NULL COMMENT '推送时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    UNIQUE KEY uk_repo_sha (repo_id, sha),
    KEY idx_project_id (project_id),
    KEY idx_matched_task_id (matched_task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='GitHub Commit记录表';

-- ================================================
-- 17. sf_watcher_heartbeat — Watcher 状态表
-- ================================================
CREATE TABLE sf_watcher_heartbeat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    watching_files INT NOT NULL DEFAULT 0 COMMENT '监控文件数',
    today_checks INT NOT NULL DEFAULT 0 COMMENT '今日检查次数',
    today_violations INT NOT NULL DEFAULT 0 COMMENT '今日违规数',
    last_beat_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后心跳时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_project_id (project_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Watcher状态表';

-- ================================================
-- 18. sf_tech_debt — 技术债表
-- ================================================
CREATE TABLE sf_tech_debt (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    task_id BIGINT DEFAULT NULL COMMENT '关联任务ID',
    title VARCHAR(200) NOT NULL COMMENT '技术债标题',
    description TEXT COMMENT '技术债描述',
    priority VARCHAR(20) NOT NULL DEFAULT 'medium' COMMENT '优先级:low/medium/high/critical',
    status VARCHAR(20) NOT NULL DEFAULT 'open' COMMENT '状态:open/in_progress/resolved/wont_fix',
    created_by BIGINT NOT NULL COMMENT '创建者用户ID',
    resolved_at DATETIME DEFAULT NULL COMMENT '解决时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_project_id (project_id),
    KEY idx_task_id (task_id),
    KEY idx_status (status),
    KEY idx_priority (priority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='技术债表';

-- ================================================
-- 19. sf_progress_snapshot — 进度快照表
-- ================================================
CREATE TABLE sf_progress_snapshot (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    project_id BIGINT NOT NULL COMMENT '项目ID',
    total_tasks INT NOT NULL DEFAULT 0 COMMENT '总任务数',
    completed_tasks INT NOT NULL DEFAULT 0 COMMENT '已完成任务数',
    completion_rate DECIMAL(5,2) NOT NULL DEFAULT 0.00 COMMENT '完成率(%)',
    snapshot_date DATE NOT NULL COMMENT '快照日期',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    UNIQUE KEY uk_project_date (project_id, snapshot_date),
    KEY idx_snapshot_date (snapshot_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='进度快照表';

-- ================================================
-- 完成
-- ================================================
