--user_role,role_menu,user_menu consrtaint delete
delete from t_user_role;
delete from t_user_menu;
delete from t_role_menu;

--role
delete from t_role;
insert  into `t_role`(`id`,`role_name`,`role_description`,`enabled`) values (1,'superadmin','超级管理员',1),(2,'manager','经理人',1);

--User
delete from t_user;
INSERT INTO t_user (id,name, password,salt,age) VALUES (1,'Gavin','98AA08A476E60D15CC75350F707CDC66','ac98bbce447b68f3',26);

--Menu
delete from t_menu;
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('1',b'1','首页','首页','0','0','/html/index.html');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('2',b'1','网站管理','网站管理','0','10','');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('3',b'1','网站管理-菜单管理','菜单管理','2','10','/html/nm/menu.html');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('4',b'1','网站管理-栏目管理','栏目管理','2','5','/html/nm/column.html');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('6',b'1','系统管理','系统管理','0','10','');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('7',b'1','系统管理-用户管理','用户管理','6','10','/html/sm/user.html');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('8',b'1','系统管理-角色管理','角色管理','6','10','/html/sm/role.html');
insert into `t_menu` (`id`, `enabled`, `menu_desc`, `menu_name`, `parant_id`, `priority`, `static_index`) values('10',b'1','内容管理','内容管理','2','5','/html/index.html');

--manage consrtaint relations
  --t_role_menu
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','1');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','2');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','3');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','4');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','6');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','7');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','8');
insert into `t_role_menu` (`role_id`, `menu_id`) values('1','10');
  --t_user_role
insert into `t_user_role` (`user_id`, `role_id`) values('1','1');
