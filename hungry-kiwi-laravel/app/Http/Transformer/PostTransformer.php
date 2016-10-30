<?php
/**
 * Created by PhpStorm.
 * User: user
 * Date: 10/9/2016
 * Time: 2:04 AM
 */

namespace App\Trans;
use App\Post;
use App\PostImage;
use App\User;
use App\Restr;
class PostTransformer extends BasicTransformer
{
    public function transform($post)
    {
        $user = User::find($post['user_id']);
        $restr_id = $post['restr_id'];
        $is_img = $is_img = $post['is_img'];

        $fPost = [
            'id' => $post['id'],
            'user_id' => $post['user_id'],
            'content' => $post['content'],
            'like_num' => $post['like_num'],
            'comment_num' => $post['comment_num'],
            'is_img' => $post['is_img'],
            'created_at' => $post['created_at'] -> toDateTimeString(),
        ];
        $fUser= [
            'first_name' => $user -> first_name,
            'last_name' => $user -> last_name,
            'img' => $user -> img,
            'is_restr' => $user -> is_restr,
        ];


        $fRestr = array();
        if($restr_id != 0) {
            $restr = Restr::find($restr_id);
            $fRestr = [
                'id' => $restr -> id,
                'name' => $restr -> name,
                'region' => $restr -> region,
                'town' => $restr -> town ,
                'street' => $restr -> street,
                'phone' => $restr -> phone,
                'mobile' => $restr -> mobile,
                'lat' => $restr -> lat,
                'long' => $restr -> long,
            ];
        }

        $fImgs = array();
        if($is_img != 0){
            $imgCollection = PostImage::getByPostId($post['id']);
            $fImgs = array_merge($fImgs, $imgCollection->all());
        }

        $result = array();
        $result = array_add($result, 'post', $fPost);
        $result = array_add($result, 'user', $fUser);
        if(count($fRestr) > 0){
            $result = array_add($result, 'restr', $fRestr);
        }
        if(count($fImgs) > 0) {
            $result = array_add($result, 'imgs', $fImgs);
        }
        return $result;
    }

    public function getImage(Post $post) {
        return $post -> postimages();
    }


}