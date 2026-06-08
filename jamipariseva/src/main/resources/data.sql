-- Sample seed data for local development (Tripura revenue hierarchy excerpt)
DELETE FROM service_request;
DELETE FROM khatian_record;
DELETE FROM ror_record;
DELETE FROM revenue_location_master;
DELETE FROM service_master;

INSERT INTO service_master (service_id, service_name, service_name_bn, fee_amount, is_active, role_id) VALUES
('10', 'Certified copy of RoR', 'রেকর্ড অব রাইটসের প্রমাণিত অনুলিপি', 50.0, true, '6'),
('11', 'Plot map extract', 'প্লট ম্যাপ অনুলিপি', 30.0, true, '6');

INSERT INTO revenue_location_master
(lgd_dist_code, dist_name_eng, lgd_subdiv_code, subdiv_name_eng, lgd_circle_code, rsname_eng, lgd_tehsil_code, tname_eng, lgd_village_code, mouname_eng)
VALUES
('272', 'West Tripura', '6696', 'Sadar', '56', 'Agartala', '8817', 'Agartala Paschim', '922855', 'Agartala Sheet No 6'),
('272', 'West Tripura', '6696', 'Sadar', '56', 'Agartala', '8816', 'Ramnagar', '271856', 'Ramnagar'),
('272', 'West Tripura', '6696', 'Sadar', '57', 'Dukli', '8820', 'Bikramnagar', '272020', 'Bikramnagar'),
('272', 'West Tripura', '6696', 'Sadar', '57', 'Dukli', '8821', 'Suryamaninagar', '272021', 'Anandanagar');

INSERT INTO ror_record (lgd_village_code, khatian_no, plot_no, sfname, slname, owner_name, total_share, mouja_name) VALUES
('922855', '11/1', '121', 'নরেন্দ্র চন্দ্র', 'পাল', 'নরেন্দ্র চন্দ্র পাল', '1/1', 'Agartala Sheet No 6'),
('922855', '12/2', '122', 'রমেশ', 'দেব', 'রমেশ দেব', '1/2', 'Agartala Sheet No 6');

INSERT INTO khatian_record (
  lgd_village_code, khatian_no, idn, ktsr, file_formate, entry_date, entered_by, entry_ip,
  verified_by, verification_date, verification_ip, approved_by, approved_date, approved_ip,
  modification_date, modified_by, modification_ip, kt1, kt2, is_cancelled
) VALUES (
  '922855', '11/1', 'IDN-922855-11-1', '11/1', 'PDF',
  CURRENT_TIMESTAMP, 1001, '127.0.0.1',
  1002, CURRENT_TIMESTAMP, '127.0.0.1', 1003, CURRENT_TIMESTAMP, '127.0.0.1',
  CURRENT_TIMESTAMP, 1001, '127.0.0.1', 11, 1, 'N'
);

INSERT INTO service_request (request_id, citizen_id, role_id, service_id, status, request_payload, pdf_url, acknowledgement_no, created_at, updated_at) VALUES
('REQ85FD94D6CA5F', '2823', '6', '10', 'success', '{}', 'http://example.com/test.pdf', 'ACK-REQ85FD94D6CA5F', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
