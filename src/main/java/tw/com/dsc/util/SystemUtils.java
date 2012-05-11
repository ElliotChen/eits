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
			AgentType at = AgentType.Guest;
			switch(role) {
			case L3Admin:
				user.setL3Admin(true);
				at = AgentType.L3;
				break;
			case L3Leader:
				user.setL3Leader(true);
				at = AgentType.L3;
				break;
			case L3Agent:
				user.setL3Agent(true);
				at = AgentType.L3;
				break;
			case L2Admin:
				user.setL2Admin(true);
				at = AgentType.L2;
				break;
			case L2Leader:
				user.setL2Leader(true);
				at = AgentType.L2;
				break;
			case L2Agent:
				user.setL2Agent(true);
				at = AgentType.L2;
				break;
			case Partner:
				user.setPartner(true);
				at = AgentType.L2;
				break;
			default:
				logger.debug("Unknow RoleId[{}]", roleValue);
			}
			logger.debug("Transfer RoleId[{}] as Role[{}]", roleValue, role);
			if (Role.Guest != role) {
				user.getUserRoles().add(new UserRole(role, ar.getDefaultGroupId(), at));
			}
		}

		if (!user.getUserRoles().isEmpty()) {
			user.setGuest(false);
			user.setCurrentUserRole(user.getUserRoles().get(0));
		}
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
