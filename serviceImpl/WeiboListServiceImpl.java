package cn.edu.bjtu.weibo.service;

import java.util.List;

import cn.edu.bjtu.weibo.model.Weibo;
import cn.edu.bjtu.weibo.dao.UserDAO;
import cn.edu.bjtu.weibo.dao.UserDAOImpl;
import cn.edu.bjtu.weibo.dao.WeiboDAO;
import cn.edu.bjtu.weibo.dao.WeiboDAOImpl;
import cn.edu.bjtu.weibo.service.RecommendWeiboService;
import cn.edu.bjtu.weibo.service.RecommendWeiboServiceImpl;


/**
 * This service work for listing weibos for user`s reading
 * 
 * @author Liu Jinfeng
 */

public class WeiboListServiceImpl implements WeiboListService {
	/**
	 * When a userId is specified, it will return the list of weibos
	 * and it needs pagination
	 * 
	 * @param userId
	 * @param pageIndex : start from 0 which means the first page.
	 * @param numberPerPage : for example, 20 or 30 weibos for each page.
	 * @return
	 */
	List<Weibo> getWeiboList(String userId, int pageIndex, int numberPerPage){
		/**
		String content;
		int like;
	    String date;
	    int commentNumber;
        String userId;
		List<String> atUserIdList;
        List<String> topicIdList;
		int forwardNumber;
		**/
		
		String WeiboID;
		Weibo weibo;
		List<Weibo> weibolist=new ArrayList<Weibo>();
		
		UserDAO userdao=new UserDAOImpl();
		WeiboDAO weibodao=new WeiboDAOImpl();
		
		//获得微博ID
		List<String> WeiboString=userdao.getWeibo(userId,pageIndex,pagePerNumber);
		//创建Weibo对象，加入微博列表
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
			weibo.setForwardNumber( weibodao.getForwardNumber(WeiboID));
			
			weibolist.add(weibo);
        }
		//返回微博列表
		return weibolist;
	};
	
	/**
	 * When a userId is not specified, it will return the random list of weibos
	 * it depends on the algorithm you think out.
	 * and it needs pagination
	 * 
	 * @param userId
	 * @param pageIndex : start from 0 which means the first page.
	 * @param numberPerPage : for example, 20 or 30 weibos for each page.
	 * @return
	 如果要是返回最热的微博是可行的，最热微博，找到粉丝人数最多的，
	 或者就像微博那样，热门
	 */
	List<Weibo> getWeiboList(String pageIndex, int numberPerPage){
		String WeiboID;
		Weibo weibo;
		List<Weibo> weibolist=new ArrayList<Weibo>();
		UserDAO userdao=new UserDAOImpl();
		List<String> userIdList;
		
	    userIdList=userdao.getUserId();
		String firtId=userIdList.get(0);
		String secondId;
		int firstIdFollowerNumber;
		int secondIdFollowerNumber;
		
		for(Iterator<String>  it=userIdList.iterator();it.hasNext();){
           secondId=it.next();
		   firstIdFollowerNumber=Integer.parseInt(usedao.getFollowerNumber(firstId));
		   secondIdFollowerNumber=Integer.parseInt(usedao.getFollowerNumber(secondId));
		   if(secondIdFollowerNumber>=firstIdFollowerNumber){
			   firstId=secondId;
		   }
        }
	 	
		return getWeiboList(firstId,pageIndex,numberPerPage);
		
	};
}
