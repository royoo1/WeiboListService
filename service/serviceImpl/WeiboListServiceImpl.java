package cn.edu.bjtu.weibo.service.serviceImpl;

import cn.edu.bjtu.weibo.service.WeiboListService;
import java.util.*;
import cn.edu.bjtu.weibo.model.Weibo;
import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.daoImpl.UserDAOImpl;
import cn.edu.bjtu.weibo.dao.WeiboDAO;
import cn.edu.bjtu.weibo.dao.daoImpl.WeiboDAOImpl;


public class WeiboListServiceImpl implements WeiboListService {
	
	public List<Weibo> getWeiboList(String userId, int pageIndex, int numberPerPage){
		
		String WeiboID;
		Weibo weibo;
		List<Weibo> weibolist=new ArrayList<Weibo>();
		
		UserDAO userdao=new UserDAOImpl();
		WeiboDAO weibodao=new WeiboDAOImpl();
		
		
		List<String> WeiboString=userdao.getWeibo(userId,pageIndex,numberPerPage);
		
		 for(Iterator<String>  it=WeiboString.iterator();it.hasNext();){
            WeiboID=it.next();
			weibo=new Weibo();
			
			weibo.setContent(weibodao.getContent(WeiboID));
			weibo.setLike(Integer.parseInt(weibodao.getLikeNumber(WeiboID)));
			weibo.setDate(weibodao.getTime(WeiboID));
			weibo.setCommentNumber(Integer.parseInt(weibodao.getCommentNumber(WeiboID)));
			weibo.setUserId(userId);
			
			weibo.setAtUserIdList(weibodao.getAtUserList(WeiboID));
			weibo.setTopicIdList(weibodao.getTopicList(WeiboID));
			weibo.setForwardNumber( Integer.parseInt(weibodao.getForwardNumber(WeiboID)));
			
			weibolist.add(weibo);
        }
		
		return weibolist;
	}
	
	
	public List<Weibo> getWeiboList(String pageIndex, int numberPerPage){
		String WeiboID;
		Weibo weibo;
		List<Weibo> weibolist=new ArrayList<Weibo>();
		UserDAO userdao=new UserDAOImpl();
		List<String> userIdList;
		
	    userIdList=userdao.getUserId();
		String firstId=userIdList.get(0);
		String secondId;
		int firstIdFollowerNumber;
		int secondIdFollowerNumber;
		
		for(Iterator<String>  it=userIdList.iterator();it.hasNext();){
           secondId=it.next();
		   firstIdFollowerNumber=Integer.parseInt(userdao.getFollowerNumber(firstId));
		   secondIdFollowerNumber=Integer.parseInt(userdao.getFollowerNumber(secondId));
		   if(secondIdFollowerNumber>=firstIdFollowerNumber){
			   firstId=secondId;
		   }
        }
	 	
		return getWeiboList(firstId,Integer.parseInt(pageIndex),numberPerPage);
		
	}
}
