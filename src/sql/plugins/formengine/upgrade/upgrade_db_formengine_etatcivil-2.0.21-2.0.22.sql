--
-- Dumping data in table formengine_group_notice
--
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (24,'naissance-coordonnees','6',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (25,'naissance-confirmation','4',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (26,'mariage-coordonnees','24',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (27,'mariage-confirmation','22',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (28,'deces-coordonnees','15',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (29,'deces-confirmation','13',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (30,'reconnaissance-coordonnees','32',1,'all');
INSERT INTO formengine_group_notice (id_group_notice,title,id_form,is_enabled,workgroup_key) VALUES 
  (31,'reconnaissance-confirmation','30',1,'all');

--
-- Dumping data in table formengine_notice
--
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (77,'naissance-coordonnees-notice','Le demandeur doit être majeur et être le titulaire de l''acte ou un de ses parents ascendant ou descendant, son conjoint, pour obtenir une copie intégrale ou un extrait avec filiation d''acte de naissance.','all',NULL,NULL,-1,1,0,0,24);
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (78,'naissance-confirmation-notice','','all',NULL,NULL,-1,1,0,0,25);
  
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (79,'mariage-coordonnees-notice','Le demandeur doit être majeur et être le titulaire de l''acte ou un de ses parents ascendant ou descendant, son conjoint, pour obtenir une copie intégrale ou un extrait avec filiation d''acte de naissance.','all',NULL,NULL,-1,1,0,0,26);
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (80,'mariage-confirmation-notice','','all',NULL,NULL,-1,1,0,0,27);
  
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (81,'deces-coordonnees-notice','Le demandeur doit être majeur et être le titulaire de l''acte ou un de ses parents ascendant ou descendant, son conjoint, pour obtenir une copie intégrale ou un extrait avec filiation d''acte de naissance.','all',NULL,NULL,-1,1,0,0,28);
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (82,'deces-confirmation-notice','','all',NULL,NULL,-1,1,0,0,29);

INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (83,'reconnaissance-coordonnees-notice','Le demandeur doit être majeur et être le titulaire de l''acte ou un de ses parents ascendant ou descendant, son conjoint, pour obtenir une copie intégrale ou un extrait avec filiation d''acte de naissance.','all',NULL,NULL,-1,1,0,0,30);
INSERT INTO formengine_notice (id_notice,title,message,workgroup_key,date_debut,date_fin,id_diffusion,is_enabled,id_notification,notice_order,id_notice_group) VALUES 
  (84,'reconnaissance-confirmation-notice','','all',NULL,NULL,-1,1,0,0,31);
