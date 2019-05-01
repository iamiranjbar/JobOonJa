import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.DriverManager;

import ir.ac.ut.dataAccess.dataMapper.auction.AuctionMapper;
import ir.ac.ut.dataAccess.dataMapper.bid.BidMapper;

import ir.ac.ut.dataAccess.dataMapper.endorse.EndorseMapper;
import ir.ac.ut.dataAccess.dataMapper.project.ProjectMapper;
import ir.ac.ut.dataAccess.dataMapper.projectSkill.ProjectSkillMapper;
import ir.ac.ut.dataAccess.dataMapper.skill.SkillMapper;
import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;
import ir.ac.ut.models.Exception.UserNotFound;
import ir.ac.ut.models.JobOonJa.JobOonJa;
import ir.ac.ut.models.Project.Project;
import ir.ac.ut.models.Skill.Skill;

import ir.ac.ut.dataAccess.dataMapper.user.UserMapper;
import ir.ac.ut.models.Skill.UserSkill;
import ir.ac.ut.models.User.User;

public class Main {
	public static void main(String[] args) throws SQLException, ClassNotFoundException, UserNotFound {
		BidMapper bidMapper = BidMapper.getInstance();
		System.out.println(bidMapper.find("4", "3"));
	}
}
