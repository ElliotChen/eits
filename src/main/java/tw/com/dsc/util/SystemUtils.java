package tw.com.dsc.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tw.com.dsc.domain.AccountRole;
import tw.com.dsc.domain.AgentType;
import tw.com.dsc.domain.Group;
import tw.com.dsc.domain.Role;
import tw.com.dsc.to.User;
import tw.com.dsc.to.UserRole;

public abstract class SystemUtils {
	private static final Logger logger = LoggerFactory.getLogger(SystemUtils.class);
	public static final Pattern L3_LEADER = Pattern.compile("^(L3_.*)_Leader$");
	public static final Pattern L3_AGENT = Pattern.compile("^(L3_.*)_Team$");
	public static final Pattern L2_ADMIN = Pattern.compile("^(L2_.*)_Admin$");
	public static final Pattern L2_LEADER = Pattern.compile("^(L2_.*)_Leader$");
	public static final Pattern L2_AGENT = Pattern.compile("^(L2_.*)_Team$");

	public static void parseRole(List<AccountRole> ars, User user) {

		for (AccountRole ar : ars) {
			String roleValue = ar.getAccountRolePK().getRoleId();
			logger.debug("Check AccountRole[{}]", roleValue);
			Role role = findRoleByValue(roleValue);
			
			logger.debug("Transfer RoleId[{}] as Role[{}]", roleValue, role);
			if (Role.Guest != role) {
				user.getUserRoles().add(new UserRole(role, parseGroupId(ar.getDefaultGroupId())));
			}
		}

		if (!user.getUserRoles().isEmpty()) {
			Role role = null;
			for (UserRole ur : user.getUserRoles()) {
				if (ur.getRole().getValue().equalsIgnoreCase(user.getDefaultRoleId())) {
					role = ur.getRole();
				}
			}
			if (null != role) {
				user.switchRole(role);
			} else {
				logger.error("Can't find Default Roie[{}] in {}", user.getDefaultRoleId(), user);
				user.switchRole(user.getUserRoles().get(0).getRole());
			}
		}
	}
	public static String parseGroupId(String gid) {
		String gname = "";
		if ("L3_Admin".equals(gid)) {
			gname = gid;
			return gname;
		}

		Matcher matcher = L3_LEADER.matcher(gid);
		if (matcher.matches()) {
			gname = matcher.group(1);
			return gname;
		}

		matcher = L3_AGENT.matcher(gid);
		if (matcher.matches()) {
			gname = matcher.group(1);
			return gname;
		}

		matcher = L2_ADMIN.matcher(gid);
		if (matcher.matches()) {
			gname = matcher.group(1);
			return gname;
		}

		matcher = L2_LEADER.matcher(gid);
		if (matcher.matches()) {
			gname = matcher.group(1);
			return gname;
		}

		matcher = L2_AGENT.matcher(gid);
		if (matcher.matches()) {
			gname = matcher.group(1);
			return gname;
		}
		return gname;
	}
	public static void parseGroup(List<Group> groups, User user) {

		String gid = "";
		String gname = "";
		for (Group group : groups) {
			gid = group.getId();
			logger.debug("Check Group[{}] for Role", gid);
			if ("L3_Admin".equals(gid)) {
				logger.debug("Group[{}] is a L3_Admin Group", gid);
				continue;
			}

			Matcher matcher = L3_LEADER.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L3_Leader for [{}]", gid, gname);
				user.getL3LeaderGroups().add(gname);
				continue;
			}

			matcher = L3_AGENT.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L3_Agent for [{}]", gid, gname);
				user.getL3AgentGroups().add(gname);
				continue;
			}

			matcher = L2_ADMIN.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L2_Admin for [{}]", gid, gname);
				user.getL2AdminGroups().add(gname);
				continue;
			}

			matcher = L2_LEADER.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L2_Leader for [{}]", gid, gname);
				user.getL2LeaderGroups().add(gname);
				continue;
			}

			matcher = L2_AGENT.matcher(gid);
			if (matcher.matches()) {
				gname = matcher.group(1);
				logger.debug("Group[{}] is a L2_Agent for [{}]", gid, gname);
				user.getL2AgentGroups().add(gname);
				continue;
			}

			logger.debug("Group[{}] is not a valid Group for eITS", gid);
		}
	}
	
	public static Role findRoleByValue(String value) {
		Role role = Role.Guest;
		
		if (Role.L2Admin.getValue().equals(value)) {
			role = Role.L2Admin;
		} else if (Role.L2Agent.getValue().equals(value)) {
			role = Role.L2Agent;
		} else if (Role.L2Leader.getValue().equals(value)) {
			role = Role.L2Leader;
		} else if (Role.L3Admin.getValue().equals(value)) {
			role = Role.L3Admin;
		} else if (Role.L3Leader.getValue().equals(value)) {
			role = Role.L3Leader;
		} else if (Role.L3Agent.getValue().equals(value)) {
			role = Role.L3Agent;
		} else if (Role.Partner.getValue().equals(value)) {
			role = Role.Partner;
		}
		
		return role;
	}
}
