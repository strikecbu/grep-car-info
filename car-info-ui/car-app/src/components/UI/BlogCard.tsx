
import type { FunctionComponent } from 'react'

import {PostCard} from "./interface";

import {Fragment} from "react";

type Props = {
    post: PostCard
}

const BlogCard: FunctionComponent<Props> = ({ post }) => {
    return (
        <Fragment>
            <a className="relative block h-full group">
        <span
            className="absolute inset-0 border-2 border-black border-dashed rounded-lg"
            aria-hidden="true"
        ></span>

                <div className="h-full transition bg-white border-2 border-black rounded-lg group-hover:-translate-x-2 group-hover:-translate-y-2">
                    <div className="px-8 pt-32 pb-8">
            <span className="text-4xl" role="img" aria-hidden="true">
              {post.emoji}
            </span>

                        <p className="mt-4 text-xl font-medium">{post.title}</p>

                        <time className="mt-1 text-xs text-gray-500"></time>
                    </div>
                </div>
            </a>
        </Fragment>
    )
}

export default BlogCard
