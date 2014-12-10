<#include "/WEB-INF/view/inc.ftl"/>

<input type="hidden" name="roleid" value="${roleid}"/>
<div class="row">
	<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
		<p>已分配用户</p>
		<table class="table table-bordered table-hover intable">
			<#if inusers?? && inusers?size gt 0>
				<#list inusers as u>
					<tr class="mytr intr" style="cursor:pointer;" data="${(u.id)!}">
						<td>${(u.user_name)!}</td>
					</tr>
				</#list>
			</#if>
		</table>
	</div>
	<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
		<div style="positoin:absolute;top:50%;height:100px;margin-top:50px;">
			<@bsbutton type="primary" icon="arrow-left"  href='javascript:web.role.addUser();'/>
			<@bsbutton type="primary" icon="arrow-right" href='javascript:web.role.removeUser();'/>
		</div>
	</div>
	<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
		<p>未分配用户</p>
		<table class="table table-bordered table-hover outtable">
			<#if outusers?? && outusers?size gt 0>
				<#list outusers as u>
					<tr class="mytr outtr" style="cursor:pointer;" data="${(u.id)!}">
						<td>${(u.user_name)!}</td>
					</tr>
				</#list>
			</#if>
		</table>
	</div>
</div>