ALTER TABLE `enti` DROP INDEX `cod_aoo_unique` ;

DELETE FROM `configuration` WHERE `name`='genova.edilizia.bo.endpoint';
DELETE FROM `configuration` WHERE `name`='genova.edilizia.bo.comunicazione.endpoint';

INSERT INTO `plugin_configuration` (`id_ente`, `name`, `value`) VALUES (2001, 'endopoint.bo.comunicazione', 'http://172.19.52.223:8080/SUE/Sue.jws');
INSERT INTO `plugin_configuration` (`id_ente`, `name`, `value`) VALUES (2001, 'endopoint.bo', 'http://172.19.52.223:8080/SUE/Sue.jws');
INSERT INTO `plugin_configuration` (`id_ente`, `name`, `value`) VALUES (2001, 'xsltTransform.bo', 'PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjx4c2w6c3R5bGVzaGVldCB2ZXJzaW9uPSIxLjAiIHhtbG5zOnhzbD0iaHR0cDovL3d3dy53My5vcmcvMTk5OS9YU0wvVHJhbnNmb3JtIg0KCXhtbG5zOnhzPSJodHRwOi8vd3d3LnczLm9yZy8yMDAxL1hNTFNjaGVtYSIgZXhjbHVkZS1yZXN1bHQtcHJlZml4ZXM9InhzIg0KCXhtbG5zOnA9Imh0dHA6Ly93d3cud2Vnby5pdC9jcm9zcyI+DQoJPHhzbDp0ZW1wbGF0ZSBtYXRjaD0icDpwcmF0aWNhIj4NCgkJPHhzbDpjb3B5Pg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmlkX3ByYXRpY2FbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9wcm9jZWRpbWVudG9fc3VhcFsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmlkZW50aWZpY2F0aXZvX3ByYXRpY2FbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpvZ2dldHRvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6cmVzcG9uc2FiaWxlX3Byb2NlZGltZW50b1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmlzdHJ1dHRvcmUiLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDp0ZXJtaW5pX2V2YXNpb25lX3ByYXRpY2FbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpub3RpZmljYVsxXSIvPg0KCQkJPCEtLSB0aXBvIHJlY2FwaW8gLS0+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6Y29kX2NhdGFzdGFsZV9jb211bmVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfY29tdW5lWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfZW50ZVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvZF9lbnRlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX2VudGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfY29tdW5lX2VudGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppbmRpcml6em9fZW50ZVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNhcF9lbnRlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZmF4X2VudGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDplbWFpbF9lbnRlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6cGVjX2VudGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDp0ZWxlZm9ub19lbnRlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfcHJvdG9jb2xsb19tYW51YWxlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6cmVnaXN0cm9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpwcm90b2NvbGxvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZmFzY2ljb2xvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6YW5ub1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmRhdGFfcHJvdG9jb2xsb1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmRhdGFfcmljZXppb25lWzFdIi8+DQoJCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmFuYWdyYWZpY2hlIi8+DQoJCQk8ZGF0aV9jYXRhc3RhbGk+DQoJCQkJPHhzbDpmb3ItZWFjaCBzZWxlY3Q9Ii9wcmF0aWNhL2RhdGlfY2F0YXN0YWxpIj4NCgkJCQkJPHhzbDpjYWxsLXRlbXBsYXRlIG5hbWU9ImRhdG9fY2F0YXN0YWxlIi8+DQoJCQkJPC94c2w6Zm9yLWVhY2g+DQoJCQkJPHhzbDpmb3ItZWFjaCBzZWxlY3Q9Ii9wcmF0aWNhL2luZGlyaXp6aV9pbnRlcnZlbnRvIj4NCgkJCQkJPHhzbDpjYWxsLXRlbXBsYXRlIG5hbWU9ImluZGlyaXp6b19pbnRlcnZlbnRvIi8+DQoJCQkJPC94c2w6Zm9yLWVhY2g+DQoJCQk8L2RhdGlfY2F0YXN0YWxpPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnByb2NlZGltZW50aVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnNjYWRlbnplWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6YWxsZWdhdGlbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpldmVudGlbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpldmVudG9fY29ycmVudGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkYXRpX2RhX2Zyb250WzFdIi8+DQoJCTwveHNsOmNvcHk+DQoJPC94c2w6dGVtcGxhdGU+DQoNCgk8eHNsOnRlbXBsYXRlIG1hdGNoPSJwOmFuYWdyYWZpY2hlIj4NCgkJPGFuYWdyYWZpY2hlPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmFuYWdyYWZpY2EiIC8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bm90aWZpY2FbMV0iLz4NCgkJCTwhLS0gcmVjYXBpdG8gLS0+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfdGlwb19ydW9sb1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvZF90aXBvX3J1b2xvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fcnVvbG9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkYXRhX2luaXppb192YWxpZGl0YVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmlkX3RpcG9fcXVhbGlmaWNhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fcXVhbGlmaWNhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzY3JpemlvbmVfdGl0b2xhcml0YVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmFzc2Vuc29fdXNvX3BlY1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnBlY1sxXSIvPg0KCQk8L2FuYWdyYWZpY2hlPg0KCTwveHNsOnRlbXBsYXRlPg0KDQoJPHhzbDp0ZW1wbGF0ZSBtYXRjaD0icDphbmFncmFmaWNhIj4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvdW50ZXJbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb25mZXJtYXRhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfYW5hZ3JhZmljYVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnRpcG9fYW5hZ3JhZmljYVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnZhcmlhbnRlX2FuYWdyYWZpY2FbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb2dub21lWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVub21pbmF6aW9uZVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOm5vbWVbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb2RpY2VfZmlzY2FsZVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnBhcnRpdGFfaXZhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZmxnX2luZGl2aWR1YWxlWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGF0YV9uYXNjaXRhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfY2l0dGFkaW5hbnphWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6Y29kX2NpdHRhZGluYW56YVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmRlc19jaXR0YWRpbmFuemFbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9uYXppb25hbGl0YVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvZF9uYXppb25hbGl0YVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmRlc19uYXppb25hbGl0YVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmlkX2NvbXVuZV9uYXNjaXRhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX2NvbXVuZV9uYXNjaXRhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfcHJvdmluY2lhX25hc2NpdGFbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfcHJvdmluY2lhX25hc2NpdGFbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9zdGF0b19uYXNjaXRhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3N0YXRvX25hc2NpdGFbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpsb2NhbGl0YV9uYXNjaXRhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6c2Vzc29bMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF90aXBvX2NvbGxlZ2lvWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fY29sbGVnaW9bMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpudW1lcm9faXNjcml6aW9uZVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmRhdGFfaXNjcml6aW9uZVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmlkX3Byb3ZpbmNpYV9pc2NyaXppb25lWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3Byb3ZpbmNpYV9pc2NyaXppb25lWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfcHJvdmluY2lhX2NjaWFhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3Byb3ZpbmNpYV9jY2lhYVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmZsZ19hdHRlc2FfaXNjcml6aW9uZV9yaVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmZsZ19vYmJsaWdvX2lzY3JpemlvbmVfcmlbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkYXRhX2lzY3JpemlvbmVfcmlbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpuX2lzY3JpemlvbmVfcmlbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpmbGdfYXR0ZXNhX2lzY3JpemlvbmVfcmVhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGF0YV9pc2NyaXppb25lX3JlYVsxXSIgLz4NCgkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOm5faXNjcml6aW9uZV9yZWFbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9mb3JtYV9naXVyaWRpY2FbMV0iIC8+DQoJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfZm9ybWFfZ2l1cmlkaWNhWzFdIiAvPg0KCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6cmVjYXBpdGlbMV0iIC8+DQoJCTwhLS0gc3RydXR0dXJhIHJlY2FwaXRpIC0tPg0KCTwveHNsOnRlbXBsYXRlPg0KDQoJPHhzbDp0ZW1wbGF0ZSBuYW1lPSJkYXRvX2NhdGFzdGFsZSI+DQoJCTxkYXRvX2NhdGFzdGFsZT4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb3VudGVyWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfaW1tb2JpbGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF90aXBvX3Npc3RlbWFfY2F0YXN0YWxlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fc2lzdGVtYV9jYXRhc3RhbGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF90aXBvX3VuaXRhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fdW5pdGFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpmb2dsaW9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDptYXBwYWxlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfdGlwb19wYXJ0aWNlbGxhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fcGFydGljZWxsYVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmVzdGVuc2lvbmVfcGFydGljZWxsYVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnN1YmFsdGVybm9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpsYXRpdHVkaW5lWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bG9uZ2l0dWRpbmVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9jb211bmVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfY29tdW5lWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bG9jYWxpdGFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9wcm92aW5jaWFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfcHJvdmluY2lhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfZHVnWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aW5kaXJpenpvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6Y2l2aWNvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6YWx0cmVfaW5mb3JtYXppb25pX2luZGlyaXp6b1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNhcFsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvZGljZV92aWFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb2RpY2VfY2l2aWNvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aW50ZXJub19udW1lcm9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppbnRlcm5vX2xldHRlcmFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppbnRlcm5vX3NjYWxhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bGV0dGVyYVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvbG9yZVsxXSIvPg0KCQk8L2RhdG9fY2F0YXN0YWxlPg0KCTwveHNsOnRlbXBsYXRlPg0KDQoJPHhzbDp0ZW1wbGF0ZSBuYW1lPSJpbmRpcml6em9faW50ZXJ2ZW50byI+DQoJCTxkYXRvX2NhdGFzdGFsZT4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb3VudGVyWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfaW1tb2JpbGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF90aXBvX3Npc3RlbWFfY2F0YXN0YWxlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fc2lzdGVtYV9jYXRhc3RhbGVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF90aXBvX3VuaXRhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fdW5pdGFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpmb2dsaW9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDptYXBwYWxlWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfdGlwb19wYXJ0aWNlbGxhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6ZGVzX3RpcG9fcGFydGljZWxsYVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmVzdGVuc2lvbmVfcGFydGljZWxsYVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOnN1YmFsdGVybm9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpsYXRpdHVkaW5lWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bG9uZ2l0dWRpbmVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9jb211bmVbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfY29tdW5lWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bG9jYWxpdGFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppZF9wcm92aW5jaWFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpkZXNfcHJvdmluY2lhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aWRfZHVnWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aW5kaXJpenpvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6Y2l2aWNvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6YWx0cmVfaW5mb3JtYXppb25pX2luZGlyaXp6b1sxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNhcFsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvZGljZV92aWFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDpjb2RpY2VfY2l2aWNvWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6aW50ZXJub19udW1lcm9bMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppbnRlcm5vX2xldHRlcmFbMV0iLz4NCgkJCTx4c2w6YXBwbHktdGVtcGxhdGVzIHNlbGVjdD0icDppbnRlcm5vX3NjYWxhWzFdIi8+DQoJCQk8eHNsOmFwcGx5LXRlbXBsYXRlcyBzZWxlY3Q9InA6bGV0dGVyYVsxXSIvPg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgc2VsZWN0PSJwOmNvbG9yZVsxXSIvPg0KCQk8L2RhdG9fY2F0YXN0YWxlPg0KCTwveHNsOnRlbXBsYXRlPg0KCQ0KCTx4c2w6dGVtcGxhdGUgbWF0Y2g9IioiID4NCgkJPHhzbDpjb3B5Pg0KCQkJPHhzbDphcHBseS10ZW1wbGF0ZXMgLz4NCgkJPC94c2w6Y29weT4NCgk8L3hzbDp0ZW1wbGF0ZT4NCjwveHNsOnN0eWxlc2hlZXQ+DQo=');

ALTER TABLE `pratica` ADD COLUMN `ufficio_riferimento` VARCHAR(255) NULL DEFAULT NULL  AFTER `id_proc_ente` ;